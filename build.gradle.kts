plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kotlin.jpa)
  alias(libs.plugins.kover)
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
}

group = properties["project.group-id"]!!
version = properties["project.version"]!!

repositories {
  mavenLocal()
  mavenCentral()
}

kotlin {
  jvmToolchain(libs.versions.java.get().toInt())
}

tasks.test {
  useJUnitPlatform()
  finalizedBy(
    tasks.named("koverHtmlReport"),
    tasks.named("koverXmlReport"),
  )
}

dependencies {
  implementation(libs.bundles.implementation)
  runtimeOnly(libs.bundles.runtimeOnly)
  testImplementation(libs.bundles.test.implementation)
}

kover {
  reports {
    filters {
      excludes {
        classes(
          "$group.trading.TradeInsightsApplicationKt",
        )
      }
    }
  }
}
