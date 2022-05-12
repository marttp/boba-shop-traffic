package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Popular
import org.springframework.data.jpa.repository.JpaRepository

interface PopularRepository : JpaRepository<Popular, Long>