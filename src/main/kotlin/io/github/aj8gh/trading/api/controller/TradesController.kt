package io.github.aj8gh.trading.api.controller

import io.github.aj8gh.trading.api.model.Trade
import io.github.aj8gh.trading.api.model.tradeFrom
import io.github.aj8gh.trading.persistence.model.entityFrom
import io.github.aj8gh.trading.persistence.repository.TradeRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val TRADES_PATH = "/trades"

private val logger = KotlinLogging.logger {}

@RestController
@RequestMapping(TRADES_PATH)
class TradesController(private val repository: TradeRepository) {

  @PostMapping
  @ResponseStatus(CREATED)
  fun postTrades(@RequestBody trades: List<Trade>): Unit = trades
    .also { logger.info { "POST $TRADES_PATH request received for ${it.size} trades" } }
    .map { entityFrom(it) }
    .let { repository.saveAll(it) }

  @GetMapping
  @ResponseStatus(OK)
  fun getTrades() = repository.findAll()
    .also { logger.info { "GET $TRADES_PATH request received, returning ${it.size} trades" } }
    .map { tradeFrom(it) }
}
