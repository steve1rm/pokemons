plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("com.diffplug.gradle.spotless")
    id("io.gitlab.arturbosch.detekt")
    id("kotlin-parcelize")
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
/*
        versionCode = 1
        versionName = "1.0"
*/

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
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
    implementation(project(":domain"))

    kotlinStdLib()
    parceler()
    logging()
    async()
    networking()
    unitTesting()
}