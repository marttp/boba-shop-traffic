package dev.tpcoder.bobashop

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.Popular
import dev.tpcoder.bobashop.model.dto.MenuWithOption
import dev.tpcoder.bobashop.model.dto.OrderPayload
import dev.tpcoder.bobashop.service.MenuService
import dev.tpcoder.bobashop.service.OrderService
import dev.tpcoder.bobashop.service.PopularService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class BobaResourceTest {

    private val menuService: MenuService = mockk()

    private val orderService: OrderService = mockk()

    private val popularService: PopularService = mockk()

    private val bobaResource: BobaResource = BobaResource(menuService, orderService, popularService)

    @Test
    @DisplayName("[Controller] Get Menu - Success")
    fun whenGetMenus_thenReturnMenus() {
        val mockMenu = listOf(
            Menu(id = 1, name = "Menu1", basePrice = BigDecimal("30.00")),
            Menu(id = 2, name = "Menu2", basePrice = BigDecimal("40.00"))
        )
        every { menuService.getMenus() } returns mockMenu
        val result = bobaResource.getMenus()
        Assertions.assertIterableEquals(mockMenu, result)
    }

    @Test
    @DisplayName("[Controller] Get Popular Menu - Success")
    fun whenGetPopularMenus_thenReturnPopular() {
        val mockPopular = listOf(
            Popular(
                id = 1,
                ranking = 1,
                menu = Menu(id = 1, name = "Menu1", basePrice = BigDecimal("30.00"))
            ),
            Popular(
                id = 2,
                ranking = 2,
                menu = Menu(id = 2, name = "Menu2", basePrice = BigDecimal("40.00"))
            )
        )
        every { popularService.getPopular() } returns mockPopular
        val result = bobaResource.getPopularMenu()
        Assertions.assertIterableEquals(mockPopular, result)
    }

    @Test
    @DisplayName("[Controller] Add New Order - Success")
    fun whenAddNewOrder_thenReturnSuccess() {
        every { orderService.orderIngress((any())) } returns Unit
        val orderPayload = OrderPayload(userId = "user1", menus = listOf(MenuWithOption(1, 2)))
        bobaResource.addOrder(orderPayload)
        verify (exactly = 1) { orderService.orderIngress(any()) }
    }
}