import Dependencies.assertjCore
import Dependencies.junit
import Dependencies.mockitoCore
import Dependencies.mockitoKotlin

plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("com.diffplug.gradle.spotless")
    id("io.gitlab.arturbosch.detekt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

spotless {
    kotlin {
        target("**/*.kt")
        trimTrailingWhitespace()
        ktlint("0.37.2").userData(mapOf("android" to "true", "color" to "true", "insert_final_newline" to "false", "reporter" to "checkstyle", "disabled_rules" to "no-wildcard-imports,max-line-length,import-ordering"))
    }
    kotlinGradle {
        target("*.gradle.kts", "additionalScripts/*.gradle.kts")
        ktlint("0.37.2").userData(mapOf("android" to "true", "color" to "true", "insert_final_newline" to "false", "reporter" to "checkstyle", "disabled_rules" to "no-wildcard-imports,max-line-length,import-ordering"))
    }
}

dependencies {
    async()

    testImplementation(assertjCore)
    testImplementation(junit)
    testImplementation(mockitoCore)
    testImplementation(mockitoKotlin)
}