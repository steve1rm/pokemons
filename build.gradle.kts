// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:_")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
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

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        config = files("${rootDir}/detekt.yml")
        parallel = true
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
