package dev.tpcoder.bobashop.model

import javax.persistence.*

@Entity(name = "menus_counter")
data class MenuCounter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    var menu: Menu? = null,

    @Column
    var count: Long = 0,
)
