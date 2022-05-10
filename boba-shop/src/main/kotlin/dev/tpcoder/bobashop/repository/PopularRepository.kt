package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Popular
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface PopularRepository : JpaRepository<Popular, UUID>