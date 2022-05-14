package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.repository.MenuRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class MenuServiceTest {

    private val menuRepository: MenuRepository = mockk()

    private val menuService: MenuService = MenuService(menuRepository)

    @Test
    @DisplayName("Get list of menu")
    fun whenGetMenus_returnSuccess() {
        every { menuRepository.findAll() } returns listOf(
            Menu(
                id = 1,
                name = "Menu1",
                basePrice = BigDecimal("30")
            )
        )
        val actual = menuService.getMenus()
        Assertions.assertTrue(actual.isNotEmpty())
        Assertions.assertEquals(1, actual[0].id)
        Assertions.assertEquals("Menu1", actual[0].name)
        Assertions.assertEquals(BigDecimal("30"), actual[0].basePrice)
    }

    @Test
    @DisplayName("Add new menu")
    fun whenAddMenu_returnSuccess() {
        val savedData = Menu(
            name = "Menu1",
            basePrice = BigDecimal("30")
        )
        val returnData = Menu(
            id = 1,
            name = "Menu1",
            basePrice = BigDecimal("30")
        )
        every { menuRepository.save(any()) } returns returnData
        val actual = menuService.addMenu(savedData)
        Assertions.assertEquals(1, actual.id)
        Assertions.assertEquals("Menu1", actual.name)
        Assertions.assertEquals(BigDecimal("30"), actual.basePrice)
    }
}