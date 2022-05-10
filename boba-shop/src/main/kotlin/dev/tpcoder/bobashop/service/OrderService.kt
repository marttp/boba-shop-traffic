package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.model.Order
import dev.tpcoder.bobashop.model.OrderMenu
import dev.tpcoder.bobashop.model.dto.MenuWithOption
import dev.tpcoder.bobashop.model.dto.OrderPayload
import dev.tpcoder.bobashop.repository.OrderMenuRepository
import dev.tpcoder.bobashop.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import javax.transaction.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderMenuRepository: OrderMenuRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(OrderService::class.java)

    fun orderIngress(orderPayload: OrderPayload) {
        logger.info("Order incoming with userId ${orderPayload.userId}")
        if (orderPayload.menus.size > 20) {
            throw InternalError("Size Exceed for system")
        }
        val menuList = mutableListOf<Menu>()
        orderPayload.menus.forEach { menuWithOp ->
            repeat(menuWithOp.amount) {
                menuList.add(Menu(id = menuWithOp.menuId))
            }
        }
        val prepareOrder = Order(
            orderDate = ZonedDateTime.now(),
            userId = orderPayload.userId,
        )
        addOrder(order = prepareOrder, menuList = orderPayload.menus)
    }

    @Transactional
    fun addOrder(order: Order, menuList: List<MenuWithOption>) {
        val savedOrder = orderRepository.save(order)
        val orderMenuList = menuList.map {
            OrderMenu(
                order = savedOrder,
                menu = Menu(id = it.menuId),
                amount = it.amount
            )
        }
        orderMenuRepository.saveAll(orderMenuList)
    }
}