package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MenuRepository : JpaRepository<Menu, UUID>