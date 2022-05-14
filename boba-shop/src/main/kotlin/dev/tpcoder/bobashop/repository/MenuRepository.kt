package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Menu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MenuRepository : JpaRepository<Menu, Long>