package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Income
import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.OrderMenu
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
import javax.transaction.Transactional


@Component
@EnableScheduling
class DeleteScheduler(
    private val menuRepository: MenuRepository,
    private val orderRepository: OrderRepository,
    private val popularRepository: PopularRepository,
    private val incomeRepository: IncomeRepository,
    private val orderMenuRepository: OrderMenuRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(DeleteScheduler::class.java)

    @Scheduled(cron = "\${cron.expression}")
    @Transactional
    fun cronScheduleTask() {
        val now = ZonedDateTime.now()
        logger.info("Start gathering information to price - {}", now)
        val menuMap = menuRepository.findAll().associate { it.id to it.basePrice }
        val sumPrice = BigDecimal.ZERO
        val orderMenuList = orderMenuRepository.findAll()
        orderMenuList.forEach {
            val profit = menuMap.getOrDefault(it.id, BigDecimal.ZERO)
            sumPrice.add(profit!!.times(it.amount.toBigDecimal()))
        }

        val ranking = getRanking(orderMenuList)
        popularRepository.deleteAll()
        popularRepository.saveAll(ranking)

        val income = Income(
            day = now.dayOfYear.minus(1).toString(),
            month = now.month.toString(),
            year = now.year.toString(),
            price = sumPrice
        )
        incomeRepository.save(income)
        // For manage storage size in Development only
        orderMenuRepository.deleteAll()
        orderRepository.deleteAll()
    }

    fun getRanking(orderMenuList: List<OrderMenu>): List<Popular> {
        val countMap = mutableMapOf<Long, Int>()
        orderMenuList.forEach { m ->
            m.id?.let {
                val key = m.menu!!.id!!
                countMap.putIfAbsent(key, 0)
                countMap[key] = countMap[key]!!.plus(m.amount)
            }
        }
        val minHeap: PriorityQueue<MutableMap.MutableEntry<Long, Int>> = PriorityQueue {
            e1, e2-> e1.value.compareTo(e2.value)
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
        return result
    }
}