package dev.tpcoder.bobashop.repository

import dev.tpcoder.bobashop.model.Income
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IncomeRepository : JpaRepository<Income, Long> {
    fun findByDayAndMonthAndYear(day: String, month: String, year: String): Income?
}