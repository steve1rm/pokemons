// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:_")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:_")
        classpath("org.jacoco:org.jacoco.core:_")
        classpath("com.google.gms:google-services:${Versions.googleServicesVersion}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}")
        val navVersion = "2.3.5"
        classpath (AndroidX.navigation.safeArgsGradlePlugin)
        classpath("com.google.firebase:firebase-appdistribution-gradle:2.2.0")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply(plugin = "jacoco")
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config = files("${rootDir}/detekt.yml")
        autoCorrect = true
        allRules = true
        buildUponDefaultConfig = true
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
