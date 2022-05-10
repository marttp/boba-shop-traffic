package dev.tpcoder.bobashop

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.Popular
import dev.tpcoder.bobashop.model.dto.OrderPayload
import dev.tpcoder.bobashop.service.MenuService
import dev.tpcoder.bobashop.service.OrderService
import dev.tpcoder.bobashop.service.PopularService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class BobaResource(
    private val menuService: MenuService,
    private val orderService: OrderService,
    private val popularService: PopularService
) {

    @GetMapping("/menus")
    fun getMenus(): List<Menu> = menuService.getMenus()

    @GetMapping("/menus/top")
    fun getPopularMenu(): List<Popular> = popularService.getPopular()

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOrder(@RequestBody orderPayload: OrderPayload) = orderService.orderIngress(orderPayload)
}