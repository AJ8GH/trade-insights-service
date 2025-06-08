package io.github.aj8gh.trading

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.aj8gh.trading.api.controller.INSIGHTS_PATH
import io.github.aj8gh.trading.api.controller.TRADES_PATH
import io.github.aj8gh.trading.config.RestClientConfig
import io.github.aj8gh.trading.persistence.repository.TradeRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.Import
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatusCode
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClient.ResponseSpec.ErrorHandler
import kotlin.test.AfterTest
import kotlin.test.Test

private val logger = KotlinLogging.logger {}

@ActiveProfiles("test")
@Import(RestClientConfig::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TradeInsightsIntegrationTest(
  @Value("\${local.server.port}") private val port: Int,
  @Autowired private val repository: TradeRepository,
  @Autowired private val mapper: ObjectMapper,
  @Autowired private val client: RestClient,
) {

  @AfterTest
  fun clearDown() {
    repository.deleteAll()
  }

  @Test
  fun postTrades() {
    makePostRequest(TRADES_PAYLOAD).statusCode shouldBeEqual CREATED
    repository.count() shouldBeEqual 2
  }

  @ParameterizedTest
  @MethodSource("badRequestDataSource")
  fun postTrades_BadRequest(tradesPayload: String) {
    makePostRequest(tradesPayload).statusCode shouldBeEqual BAD_REQUEST
    repository.count() shouldBe 0
  }

  @Test
  fun getTrades() {
    makePostRequest(TRADES_PAYLOAD)
    makeGetRequest(TRADES_PATH).let {
      it.statusCode shouldBeEqual OK
      it.body!! shouldBeEqual readValue(TRADES_PAYLOAD)
    }
  }

  @ParameterizedTest
  @MethodSource("insightsDataSource")
  fun getInsights(tradesPayload: String, insightsResponse: String) {
    makePostRequest(tradesPayload)
    makeGetRequest(INSIGHTS_PATH).let {
      it.statusCode shouldBeEqual OK
      it.body shouldBeEqual readValue(insightsResponse)
    }
  }

  companion object {
    @JvmStatic
    private fun insightsDataSource() = listOf(
      Arguments.of(TRADES_PAYLOAD, INSIGHTS_RESPONSE),
      Arguments.of(TRADES_PAYLOAD_1, INSIGHTS_RESPONSE_1),
    )

    @JvmStatic
    private fun badRequestDataSource() = BAD_REQUEST_PAYLOADS.map { Arguments.of(it) }
  }

  private fun makePostRequest(request: String) = client.post()
    .uri(uri(TRADES_PATH))
    .body(request)
    .retrieve()
    .onStatus(HttpStatusCode::isError, errorHandler())
    .toBodilessEntity()

  private fun makeGetRequest(path: String) = client.get()
    .uri(uri(path))
    .retrieve()
    .toEntity(object : ParameterizedTypeReference<Any>() {})

  private fun uri(path: String) = "http://localhost:$port$path"

  private fun readValue(value: String) =
    mapper.readValue(value, object : TypeReference<Any>() {})

  private fun errorHandler(): ErrorHandler {
    return ErrorHandler { req, res ->
      logger.error {
        "Received ${res.statusCode} response from ${req.method} request to ${req.uri}"
      }
    }
  }
}
