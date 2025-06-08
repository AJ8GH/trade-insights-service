package io.github.aj8gh.trading.api.model

import io.github.aj8gh.trading.persistence.model.TradeEntity
import java.math.BigDecimal
import java.time.Instant

data class Trade(
  val commodity: String,
  val traderId: String,
  val price: BigDecimal,
  val quantity: Int,
  val timestamp: Instant,
)

fun tradeFrom(entity: TradeEntity) = Trade(
  commodity = entity.commodity,
  traderId = entity.traderId,
  price = entity.price,
  quantity = entity.quantity,
  timestamp = entity.timestamp,
)
