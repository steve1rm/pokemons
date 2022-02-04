
plugins {
    id("com.android.application")
    //   id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.diffplug.gradle.spotless")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
}

android {
    compileSdkVersion(31)
    buildToolsVersion = Versions.buildToolsVersion

    defaultConfig {
        applicationId = "me.androidbox.pokemon"
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 2
        versionName = "1.1"

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

            firebaseAppDistribution {
                serviceCredentialsFile = "app/pokemon-app-distribution.json"
                appId = "1:219955073766:android:ed83db833b259ba6e96127"
                releaseNotes = "Release One with groups"
                groups = "PokemonTesters"
            }
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
    implementation(project(":domain"))
    implementation(project(":data"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")

    implementation(platform("com.google.firebase:firebase-bom:29.0.3"))
    implementation("com.google.firebase:firebase-analytics-ktx")

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