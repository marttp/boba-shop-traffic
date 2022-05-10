package dev.tpcoder.bobashop.model

import javax.persistence.*

@Entity(name = "populars")
data class Popular(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var ranking: Int = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    var menu: Menu? = null

)
