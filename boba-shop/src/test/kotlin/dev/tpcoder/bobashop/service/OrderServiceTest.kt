package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.Order
import dev.tpcoder.bobashop.model.OrderMenu
import dev.tpcoder.bobashop.model.dto.MenuWithOption
import dev.tpcoder.bobashop.repository.OrderMenuRepository
import dev.tpcoder.bobashop.repository.OrderRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.ZonedDateTime

internal class OrderServiceTest {

    private val orderRepository: OrderRepository = mockk()
    private val orderMenuRepository: OrderMenuRepository = mockk()
    private val orderService: OrderService = OrderService(orderRepository, orderMenuRepository)

    @Test
    @DisplayName("Order Ingress - Success")
    fun whenOrderIncoming_returnSuccess() {

    }

    @Test
    @DisplayName("Order limitation exceed")
    fun whenOrderIncomingTooMuch_returnError() {

    }

    @Test
    @DisplayName("Add order")
    fun whenAddOrder_returnSuccess() {
        val mockOrder = Order(
            id = 1,
            orderDate = ZonedDateTime.now(),
            userId = "user1"
        )
        every { orderRepository.save(any()) } returns mockOrder
        val mockMenu = Menu(
            id = 1
        )
        val orderMenu = OrderMenu(
            order = mockOrder,
            menu = mockMenu,
            amount = 2
        )
        val afterOrderMenu = OrderMenu(
            id = 1,
            order = mockOrder,
            menu = mockMenu,
            amount = 2
        )
        every { orderMenuRepository.saveAll(listOf(orderMenu)) } returns listOf(afterOrderMenu)
        orderService.addOrder(mockOrder, listOf(MenuWithOption(mockMenu.id!!, 2)))
        verify(exactly = 1) { orderRepository.save(any()) }
        verify(exactly = 1) { orderMenuRepository.saveAll(listOf(orderMenu)) }
    }
}