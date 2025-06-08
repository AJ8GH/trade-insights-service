package io.github.aj8gh.trading.persistence.repository

import io.github.aj8gh.trading.persistence.model.TradeEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TradeRepository : JpaRepository<TradeEntity, UUID>
