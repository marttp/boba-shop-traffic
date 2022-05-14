package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.*
import dev.tpcoder.bobashop.repository.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.ZonedDateTime

internal class DeleteSchedulerTest {

    private val menuRepository: MenuRepository = mockk()
    private val orderRepository: OrderRepository = mockk()
    private val popularRepository: PopularRepository = mockk()
    private val incomeRepository: IncomeRepository = mockk()
    private val orderMenuRepository: OrderMenuRepository = mockk()
    private val menuCounterRepository: MenuCounterRepository = mockk()

    private val deleteScheduler: DeleteScheduler = DeleteScheduler(
        menuRepository,
        orderRepository,
        popularRepository,
        incomeRepository,
        orderMenuRepository,
        menuCounterRepository
    )

    @Test
    @DisplayName("Gathering price - Income exist")
    fun whenGatherPrice_completed() {
        val now = ZonedDateTime.now()
        // Given
        val initList = listOf(
            Menu(id = 1, name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
            Menu(id = 2, name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
            Menu(id = 3, name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
        )
        every { menuRepository.findAll() } returns initList
        val mockOrder = Order(id = 1, orderDate = now, userId = "user1")
        val orderMenuList = listOf(
            OrderMenu(id = 1, menu = initList[0], order = mockOrder, amount = 2),
            OrderMenu(id = 2, menu = initList[1], order = mockOrder, amount = 1),
        )
        every { orderMenuRepository.findAll() } returns orderMenuList
        val menuCounterList = listOf(
            MenuCounter(id = 1, menu = initList[0], count = 2),
            MenuCounter(id = 2, menu = initList[1], count = 1)
        )
        every { menuCounterRepository.findAll() } returns listOf()
        every { menuCounterRepository.saveAll(any<List<MenuCounter>>()) } returns menuCounterList
        val queryIncome = Income(
            id = 1,
            day = now.dayOfMonth.toString(),
            month = now.month.toString(),
            year = now.year.toString(),
            price = BigDecimal("30")
        )
        every { incomeRepository.findByDayAndMonthAndYear(any(), any(), any()) } returns queryIncome
        val newIncome = Income(
            id = 1,
            day = now.dayOfMonth.toString(),
            month = now.month.toString(),
            year = now.year.toString(),
            price = BigDecimal("130")
        )
        every { incomeRepository.save(any()) } returns newIncome
        every { orderMenuRepository.deleteAll() } returns Unit
        every { orderRepository.deleteAll() } returns Unit

        // Execute [When]
        deleteScheduler.cronScheduleTask()

        // Then
        verify(exactly = 1) { menuRepository.findAll() }
        verify(exactly = 1) { orderMenuRepository.findAll() }
        verify(exactly = 1) { menuCounterRepository.saveAll(any<List<MenuCounter>>()) }
        verify(exactly = 1) { incomeRepository.findByDayAndMonthAndYear(any(), any(), any()) }
        verify(exactly = 1) { incomeRepository.save(any()) }
        verify(exactly = 1) { orderMenuRepository.deleteAll() }
        verify(exactly = 1) { orderRepository.deleteAll() }
    }

    @Test
    @DisplayName("Gathering price - Income doesn't exist")
    fun whenGatherPrice_IncomeNotExist_completed() {
        val now = ZonedDateTime.now()
        // Given
        val initList = listOf(
            Menu(id = 1, name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
            Menu(id = 2, name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
            Menu(id = 3, name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
        )
        every { menuRepository.findAll() } returns initList
        val mockOrder = Order(id = 1, orderDate = now, userId = "user1")
        val orderMenuList = listOf(
            OrderMenu(id = 1, menu = initList[0], order = mockOrder, amount = 2),
            OrderMenu(id = 2, menu = initList[1], order = mockOrder, amount = 1),
        )
        every { orderMenuRepository.findAll() } returns orderMenuList
        val menuCounterList = listOf(
            MenuCounter(id = 1, menu = initList[0], count = 2),
            MenuCounter(id = 2, menu = initList[1], count = 1)
        )
        every { menuCounterRepository.findAll() } returns listOf()
        every { menuCounterRepository.saveAll(any<List<MenuCounter>>()) } returns menuCounterList
        every { incomeRepository.findByDayAndMonthAndYear(any(), any(), any()) } returns null
        val newIncome = Income(
            id = 1,
            day = now.dayOfMonth.toString(),
            month = now.month.toString(),
            year = now.year.toString(),
            price = BigDecimal("100")
        )
        every { incomeRepository.save(any()) } returns newIncome
        every { orderMenuRepository.deleteAll() } returns Unit
        every { orderRepository.deleteAll() } returns Unit

        // Execute [When]
        deleteScheduler.cronScheduleTask()

        // Then
        verify(exactly = 1) { menuRepository.findAll() }
        verify(exactly = 1) { orderMenuRepository.findAll() }
        verify(exactly = 1) { menuCounterRepository.saveAll(any<List<MenuCounter>>()) }
        verify(exactly = 1) { incomeRepository.findByDayAndMonthAndYear(any(), any(), any()) }
        verify(exactly = 1) { incomeRepository.save(any()) }
        verify(exactly = 1) { orderMenuRepository.deleteAll() }
        verify(exactly = 1) { orderRepository.deleteAll() }
    }

    @Test
    @DisplayName("Manage popular for menus")
    fun whenManagePopular_completed() {
        val menu1 = Menu(id = 1, name = "Menu1")
        val menu2 = Menu(id = 2, name = "Menu2")
        val menuCounter = listOf(
            MenuCounter(id = 1, menu = menu1, count = 10),
            MenuCounter(id = 2, menu = menu2, count = 15)
        )
        every { menuCounterRepository.findAll() } returns menuCounter
        every { popularRepository.deleteAll() } returns Unit
        val popularList = listOf(
            Popular(ranking = 2, menu = Menu(id = 1)),
            Popular(ranking = 1, menu = Menu(id = 2))
        )
        every { popularRepository.saveAll(any<List<Popular>>()) } returns popularList

        // Execute
        deleteScheduler.cronScheduleTaskForPopular()

        verify(exactly = 1) { menuCounterRepository.findAll() }
        verify(exactly = 1) { popularRepository.deleteAll() }
        verify(exactly = 1) { popularRepository.saveAll(any<List<Popular>>()) }
    }

}