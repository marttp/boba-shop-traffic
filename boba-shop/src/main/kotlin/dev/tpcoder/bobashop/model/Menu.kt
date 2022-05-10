package dev.tpcoder.bobashop.model

import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "menus")
data class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var name: String? = null,

    @Column
    var basePrice: BigDecimal? = null
)
