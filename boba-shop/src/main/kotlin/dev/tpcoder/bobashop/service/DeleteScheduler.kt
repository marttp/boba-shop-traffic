package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Income
import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.MenuCounter
import dev.tpcoder.bobashop.model.Popular
import dev.tpcoder.bobashop.repository.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*


@Component
@EnableScheduling
class DeleteScheduler(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository,
    private val popularRepository: PopularRepository,
    private val incomeRepository: IncomeRepository,
    private val orderMenuRepository: OrderMenuRepository,
    private val menuCounterRepository: MenuCounterRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(DeleteScheduler::class.java)

    @Scheduled(cron = "\${cron.expression}")
    fun cronScheduleTask() {
        val now = ZonedDateTime.now()
        logger.info("Start gathering information to price - {}", now)

        val menuMap = menuRepository.findAll().associate { it.id to it.basePrice }
        val sumPrice = BigDecimal.ZERO
        val orderMenuList = orderMenuRepository.findAll()
        val menuCounter = menuCounterRepository.findAll()
            .associateBy { it.menu!!.id }
            .toMutableMap()

        orderMenuList.forEach {
            val profit = menuMap.getOrDefault(it.menu!!.id, BigDecimal.ZERO)
            sumPrice.add(profit!!.times(it.amount.toBigDecimal()))
            menuCounter.putIfAbsent(it.menu!!.id, MenuCounter(menu = it.menu!!, count = 0))
            menuCounter[it.menu!!.id]!!.count.plus(1)
        }
        menuCounterRepository.saveAll(menuCounter.values)

        val day = now.dayOfMonth.toString()
        val month = now.month.toString()
        val year = now.year.toString()
        val queryIncome =
            incomeRepository.findByDayAndMonthAndYear(day = day, month = month, year = year)
        if (queryIncome != null) {
            queryIncome.price.add(sumPrice)
            incomeRepository.save(queryIncome)
        } else {
            val income = Income(
                day = day,
                month = month,
                year = year,
                price = sumPrice
            )
            logger.debug("Write new income data $income")
            incomeRepository.save(income)
        }
        // For manage storage size in Development only
        orderMenuRepository.deleteAll()
        orderRepository.deleteAll()
    }

    @Scheduled(cron = "\${cron.popular}")
    fun cronScheduleTaskForPopular() {
        val menuCounterList = menuCounterRepository.findAll()
        val countMap = mutableMapOf<Long, Long>()
        menuCounterList.forEach {
            val id = it.menu!!.id!!
            countMap[id] = it.count
        }
        val minHeap: PriorityQueue<MutableMap.MutableEntry<Long, Long>> = PriorityQueue { e1, e2 ->
            e1.value.compareTo(e2.value)
        }
        countMap.entries.forEach {
            minHeap.add(it)
            if (minHeap.size > 5) {
                minHeap.poll()
            }
        }
        val result = mutableListOf<Popular>()
        while (!minHeap.isEmpty()) {
            val rank = minHeap.size
            val popular = minHeap.poll()
            result.add(Popular(ranking = rank, menu = Menu(id = popular.key)))
        }
        popularRepository.deleteAll()
        popularRepository.saveAll(result)
    }

    @Scheduled(cron = "\${cron.weekly-del-popular}")
    fun cronScheduleTaskForClearPopular() {
        popularRepository.deleteAll()
    }
}