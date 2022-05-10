package dev.tpcoder.bobashop.model.dto

data class OrderPayload(val userId: String, val menus: List<MenuWithOption>)
