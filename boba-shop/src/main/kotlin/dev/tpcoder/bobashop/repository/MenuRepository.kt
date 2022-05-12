package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long>