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

dependencies {
    async()

    testImplementation(assertjCore)
    testImplementation(junit)
    testImplementation(mockitoCore)
    testImplementation(mockitoKotlin)
}