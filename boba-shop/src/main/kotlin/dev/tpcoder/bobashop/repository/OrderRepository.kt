package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>