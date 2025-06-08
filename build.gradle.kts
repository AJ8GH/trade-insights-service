plugins {
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.spring)
  alias(libs.plugins.kotlin.jpa)
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
}

dependencies {
  implementation(libs.bundles.implementation)
  runtimeOnly(libs.bundles.runtimeOnly)
  testImplementation(libs.bundles.test.implementation)
}
