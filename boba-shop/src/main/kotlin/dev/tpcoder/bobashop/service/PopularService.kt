package dev.tpcoder.bobashop.service

import dev.tpcoder.bobashop.model.Popular
import dev.tpcoder.bobashop.repository.PopularRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class PopularService(private val popularRepository: PopularRepository) {
    private val logger: Logger = LoggerFactory.getLogger(PopularService::class.java)

    fun getPopular(): List<Popular> {
        logger.info("Get Popular working")
        return popularRepository.findAll(Sort.by(Sort.Direction.ASC, "ranking"))
    }
}