package dev.tpcoder.bobashop

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.repository.MenuRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.math.BigDecimal

class InitDataTest {
    private val menuRepository: MenuRepository = mockk()
    private val initData: InitData = InitData(menuRepository)

    @Test
    fun whenExisting_doNothing() {
        val initList = listOf(
            Menu(name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
            Menu(name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
            Menu(name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
        )
        every { menuRepository.findAll() } returns initList
        initData.run(args = null)
        verify(exactly = 1) { menuRepository.findAll() }
    }

    @Test
    fun whenNothing_doSave() {
        val initList = listOf(
            Menu(name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
            Menu(name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
            Menu(name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
        )
        val resultList = listOf(
            Menu(id = 1, name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
            Menu(id = 2, name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
            Menu(id = 3, name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
        )
        every { menuRepository.findAll() } returns listOf()
        every { menuRepository.saveAll(initList) } returns resultList
        initData.run(args = null)
        verify(exactly = 1) { menuRepository.findAll() }
        verify(exactly = 1) { menuRepository.saveAll(initList) }
    }
}