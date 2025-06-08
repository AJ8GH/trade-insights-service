package io.github.aj8gh.trading.api.model

import java.math.BigDecimal

data class TradeInsights(
  val totalVolumeByCommodity: Map<String, Int>,
  val averagePriceByCommodity: Map<String, BigDecimal>,
  val topTradersByVolume: List<Trader>,
) {

  data class Trader(
    val traderId: String,
    val volume: Int,
  ) {

    fun addVolume(other: Trader) = Trader(
      traderId = traderId,
      volume = volume + other.volume,
    )
  }
}

fun traderFrom(trade: Trade) = TradeInsights.Trader(
  traderId = trade.traderId,
  volume = trade.quantity,
)
