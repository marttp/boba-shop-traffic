package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.Popular
import dev.tpcoder.bobashop.repository.PopularRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class PopularServiceTest {

    private val popularRepository: PopularRepository = mockk()

    private val popularService: PopularService = PopularService(popularRepository)

    @Test
    @DisplayName("Get Popular - Success")
    fun whenGetPopular_returnSuccess() {
        every { popularService.getPopular() } returns listOf(
            Popular(
                id = 1,
                ranking = 1,
                Menu(id = 1, name = "Menu1", basePrice = BigDecimal("30"))
            ),
            Popular(
                id = 2,
                ranking = 2,
                Menu(id = 2, name = "Menu2", basePrice = BigDecimal("40"))
            )
        )
        val actual = popularService.getPopular()
        Assertions.assertEquals(2, actual.size)
        Assertions.assertEquals(1, actual[0].ranking)
        Assertions.assertEquals(2, actual[1].ranking)
    }
}