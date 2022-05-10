package dev.tpcoder.bobashop.model

import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "incomes")
data class Income(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val day: String,

    @Column(nullable = false)
    val month: String,

    @Column(nullable = false)
    val year: String,

    @Column(nullable = false)
    val price: BigDecimal
)
