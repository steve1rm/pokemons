
plugins {
    id("com.android.application")
 //   id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
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
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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