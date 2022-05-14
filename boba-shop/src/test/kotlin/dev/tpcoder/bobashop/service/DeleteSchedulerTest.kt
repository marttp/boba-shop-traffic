package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.repository.*
import io.mockk.mockk

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
}