package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Menu
import dev.tpcoder.bobashop.repository.MenuRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MenuService(private val menuRepository: MenuRepository) {
    private val logger: Logger = LoggerFactory.getLogger(MenuService::class.java)

    fun getMenus(): List<Menu> = menuRepository.findAll();

    fun addMenu(menu: Menu) = menuRepository.save(menu);
}