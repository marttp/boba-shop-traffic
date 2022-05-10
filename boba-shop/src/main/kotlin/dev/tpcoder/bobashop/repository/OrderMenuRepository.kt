package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.OrderMenu
import org.springframework.data.jpa.repository.JpaRepository

interface OrderMenuRepository : JpaRepository<OrderMenu, Long>