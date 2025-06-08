package io.github.aj8gh.trading.insights

import io.github.aj8gh.trading.api.model.Trade
import io.github.aj8gh.trading.api.model.TradeInsights
import io.github.aj8gh.trading.api.model.tradeFrom
import io.github.aj8gh.trading.api.model.traderFrom
import io.github.aj8gh.trading.persistence.repository.TradeRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

private val logger = KotlinLogging.logger {}

@Service
class InsightsService(
  private val repository: TradeRepository,
  @Value("\${trade.price.scale}") private val scale: Int
) {

  fun getTradeInsights() = repository.findAll()
    .map { tradeFrom(it) }
    .let {
      val tradesByCommodity = getTradesByCommodity(it)
      TradeInsights(
        totalVolumeByCommodity = totalVolumeByCommodity(tradesByCommodity),
        averagePriceByCommodity = averagePriceByCommodity(tradesByCommodity),
        topTradersByVolume = topTradersByVolume(it),
      )
    }

  private fun getTradesByCommodity(trades: List<Trade>) = trades
    .groupBy { it.commodity }
    .also {
      logger.info {
        "Calculating insights on ${trades.size} trades, across ${it.size} commodities"
      }
    }

  private fun totalVolumeByCommodity(trades: Map<String, List<Trade>>) = trades
    .mapValues {
      it.value.sumOf { trade -> trade.quantity }
    }

  private fun averagePriceByCommodity(trades: Map<String, List<Trade>>) = trades
    .mapValues {
      it.value
        .sumOf { trade -> trade.price }
        .divide(BigDecimal(it.value.size), scale, HALF_UP)
    }

  private fun topTradersByVolume(trades: List<Trade>) = trades
    .map { traderFrom(it) }
    .groupingBy { it.traderId }
    .reduce { _, acc, trader -> acc.addVolume(trader) }
    .values.sortedByDescending { it.volume }
}
