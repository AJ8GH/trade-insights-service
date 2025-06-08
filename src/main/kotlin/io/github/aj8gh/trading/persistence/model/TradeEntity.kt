package io.github.aj8gh.trading.persistence.model

import io.github.aj8gh.trading.api.model.Trade
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
data class TradeEntity(
  @Id @UuidGenerator val id: UUID? = null,
  @CreationTimestamp val createdAt: Instant? = null,
  @UpdateTimestamp val updatedAt: Instant? = null,
  val commodity: String,
  val traderId: String,
  val price: BigDecimal,
  val quantity: Int,
  val timestamp: Instant,
)

fun entityFrom(trade: Trade) = TradeEntity(
  commodity = trade.commodity,
  traderId = trade.traderId,
  price = trade.price,
  quantity = trade.quantity,
  timestamp = trade.timestamp,
)
