package io.github.aj8gh.trading.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.client.JdkClientHttpRequestFactory
import org.springframework.web.client.RestClient

@TestConfiguration
class RestClientConfig {

  @Bean
  fun testRestClient(): RestClient = RestClient.builder()
    .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
    .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
    .requestFactory(JdkClientHttpRequestFactory())
    .build()
}
