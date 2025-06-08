package io.github.aj8gh.trading.api.controller

import io.github.aj8gh.trading.insights.InsightsService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val INSIGHTS_PATH = "/insights"

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(INSIGHTS_PATH)
class InsightsController(private val service: InsightsService) {

  @GetMapping
  @ResponseStatus(OK)
  fun insights() = logger
    .info { "GET $INSIGHTS_PATH request received" }
    .let { service.getTradeInsights() }
}
