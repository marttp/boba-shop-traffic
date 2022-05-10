package dev.tpcoder.bobashop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class BobaShopApplication
fun main(args: Array<String>) {
	runApplication<BobaShopApplication>(*args)
}
