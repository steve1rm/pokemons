
plugins {
    id("com.android.application")
    //   id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.diffplug.gradle.spotless")
    id("io.gitlab.arturbosch.detekt")
}

android {
    compileSdkVersion(31)
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        applicationId = "me.androidbox.pokemon"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(mapOf("room.incremental" to "true"))
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    /* Android 4.0 */
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
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
    appcompat()
    kotlinStdLib()
    lifecycleExtensions()
    parceler()
    constraintLayout()
    navigation()
    logging()
    shimmer()
    glide()
    dependencyInjection()
    async()
    networking()
    epoxy()
    unitTesting()
    UITesting()
}