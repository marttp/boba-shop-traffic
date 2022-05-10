package dev.tpcoder.bobashop.model


import java.time.ZonedDateTime
import javax.persistence.*

@Entity(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val orderDate: ZonedDateTime,

    @Column(nullable = false)
    val userId: String
)
