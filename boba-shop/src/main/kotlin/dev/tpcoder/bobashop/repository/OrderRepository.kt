package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long>