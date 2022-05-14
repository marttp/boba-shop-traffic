package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.MenuCounter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuCounterRepository : JpaRepository<MenuCounter, Long>