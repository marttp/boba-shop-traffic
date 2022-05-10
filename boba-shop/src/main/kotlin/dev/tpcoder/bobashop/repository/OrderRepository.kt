package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderRepository : JpaRepository<Order, UUID>