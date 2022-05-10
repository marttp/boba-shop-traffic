package dev.tpcoder.bobashop

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.repository.MenuRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class InitData(private val menuRepository: MenuRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val existMenu = menuRepository.findAll()
        if (existMenu.isEmpty()) {
            val initList = listOf(
                Menu(name = "Taiwan Milk Tea", basePrice = BigDecimal("30.0")),
                Menu(name = "Brown Sugar Milk Tea", basePrice = BigDecimal("40.0")),
                Menu(name = "Cocoa Milk Tea", basePrice = BigDecimal("30.0")),
            )
            menuRepository.saveAll(initList)
        }
    }
}