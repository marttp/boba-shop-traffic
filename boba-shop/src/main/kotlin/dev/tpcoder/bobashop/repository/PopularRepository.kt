package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Popular
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PopularRepository : JpaRepository<Popular, Long>