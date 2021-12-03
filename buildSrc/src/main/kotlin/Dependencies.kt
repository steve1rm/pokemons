import Dependencies.adapterRxjava2
import Dependencies.assertjCore
import Dependencies.converterGson
import Dependencies.coreTesting
import Dependencies.daggerAndroidProcessor
import Dependencies.daggerCompiler
import Dependencies.dagger
import Dependencies.daggerAndroidSupport
import Dependencies.junit
import Dependencies.epoxy
import Dependencies.epoxyPaging
import Dependencies.epoxyProcessor
import Dependencies.espressoCore
import Dependencies.espressoIntents
import Dependencies.fragmentTesting
import Dependencies.glideCompiler
import Dependencies.glide
import Dependencies.gson
import Dependencies.kakao
import Dependencies.loggingInterceptor
import Dependencies.mockWebServer
import Dependencies.mockitoCore
import Dependencies.mockitoKotlin
import Dependencies.navigationFragmentKtx
import Dependencies.navigationUiKtx
import Dependencies.okhttp
import Dependencies.retrofit
import Dependencies.robolectric
import Dependencies.rxAndroid
import Dependencies.rxBinding
import Dependencies.rxJava
import Dependencies.rxKotlin
import Dependencies.rxRelay
import Dependencies.testCore
import Dependencies.testExtJunit
import Dependencies.testRunner
import Dependencies.shimmer
import Dependencies.timber
import Dependencies.constraintLayout
import Dependencies.parcelerApi
import Dependencies.parceler
import Dependencies.lifecycleExtensions
import Dependencies.kotlinStdlib
import Dependencies.appCompat

import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayoutVersion}"

    // Networking
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val adapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    // Aysnc
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxjavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlinVersion}"
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:${Versions.rxRelayVersion}"
    const val rxRelay = "com.jakewharton.rxrelay3:rxrelay:${Versions.rxRelayVersion}"

    // Dagger
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerAndroidVersion}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerAndroidVersion}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerAndroidVersion}"
    const val dagger = "com.google.dagger:dagger:${Versions.daggerAndroidVersion}"

    // Epoxy
    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxyVersion}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxyVersion}"
    const val epoxyPaging = "com.airbnb.android:epoxy-paging:${Versions.epoxyVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val parceler = "org.parceler:parceler:${Versions.parcelerVersion}"
    const val parcelerApi = "org.parceler:parceler-api:${Versions.parcelerVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponentVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponentVersion}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}"

    // Unit Testing
    const val junit = "junit:junit:${Versions.junitVersion}"
    const val assertjCore = "org.assertj:assertj-core:${Versions.assertJVersion}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTestingVersion}"
    const val testCore = "androidx.test:core:${Versions.androidxCoreVersion}"
    const val testRunner = "androidx.test:runner:${Versions.runnerVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.testExtJunitVersion}"

    // Instrumentation Testing
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentVersion}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoCoreVersion}"
    const val kakao = "io.github.kakaocup:kakao:${Versions.kakaoVersion}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServerVersion}"
}

fun DependencyHandler.appcompat() {
    implementation(appCompat)
}

fun DependencyHandler.kotlinStdLib() {
    implementation(kotlinStdlib)
}

fun DependencyHandler.lifecycleExtensions() {
    implementation(lifecycleExtensions)
}

fun DependencyHandler.parceler() {
    implementation(parceler)
    kapt(parcelerApi)
}

fun DependencyHandler.constraintLayout() {
    implementation(constraintLayout)
}

fun DependencyHandler.navigation() {
    implementation(navigationFragmentKtx)
    implementation(navigationUiKtx)
}

fun DependencyHandler.logging() {
    implementation(timber)
}

fun DependencyHandler.shimmer() {
    implementation(shimmer)
}

fun DependencyHandler.glide() {
    implementation(glide)
    kapt(glideCompiler)
}

fun DependencyHandler.dependencyInjection() {
    kapt(daggerCompiler)
    kapt(daggerAndroidProcessor)
    implementation(daggerAndroidSupport)
    implementation(dagger)
}

fun DependencyHandler.async() {
    implementation(rxJava)
    implementation(rxAndroid)
    implementation(rxKotlin)
    implementation(rxBinding)
    implementation(rxRelay)
}

fun DependencyHandler.networking() {
    implementation(converterGson)
    implementation(retrofit)
    implementation(adapterRxjava2)
    implementation(okhttp)
    implementation(loggingInterceptor)
    implementation(gson)
}

fun DependencyHandler.epoxy() {
    implementation(epoxy)
    kapt(epoxyProcessor)
    implementation(epoxyPaging)
}

fun DependencyHandler.unitTesting() {
    testImplementation(assertjCore)
    testImplementation(junit)
    testImplementation(mockitoCore)
    testImplementation(mockitoKotlin)
    testImplementation(coreTesting)
    testImplementation(testCore)
    testImplementation(testCore)
    testImplementation(testRunner)
    testImplementation(robolectric)
    testImplementation(testExtJunit)
    testImplementation(dagger)
    testImplementation(daggerAndroidSupport)
    kaptTest(daggerAndroidProcessor)
    kaptTest(daggerCompiler)
}

fun DependencyHandler.UITesting() {
    androidTestImplementation(kakao)
    androidTestImplementation(mockWebServer)
    androidTestImplementation(espressoCore)
    androidTestImplementation(espressoIntents)
    androidTestImplementation(fragmentTesting)
    androidTestImplementation(dagger)
    androidTestImplementation(daggerAndroidSupport)
    kaptAndroidTest(daggerAndroidProcessor)
    kaptAndroidTest(daggerCompiler)
}