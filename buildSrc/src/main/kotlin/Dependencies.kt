object Dependencies {

    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.0"
    const val appCompat = "androidx.appcompat:appcompat:1.1.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"

    // Networking
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val adapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}"

    // Aysnc
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxjavaVersion}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxkotlinVersion}"
    const val rxBinding = "com.jakewharton.rxbinding3:rxbinding:3.0.0"
    const val rxRelay = "com.jakewharton.rxrelay3:rxrelay:3.0.0"

    // Dagger
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.daggerAndroidVersion}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerAndroidVersion}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerAndroidVersion}"
    const val dagger = "com.google.dagger:dagger:${Versions.daggerAndroidVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleVersion}"
    const val parceler = "org.parceler:parceler:${Versions.parcelerVersion}"
    const val parcelerApi = "org.parceler:parceler-api:${Versions.parcelerVersion}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"

    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComponentVersion}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationComponentVersion}"

    // Testing
    const val espressCore = "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"
    const val expressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoCoreVersion}"

    const val epoxy = "com.airbnb.android:epoxy:${Versions.epoxyVersion}"
    const val epoxyProcessor = "com.airbnb.android:epoxy-processor:${Versions.epoxyVersion}"
    const val epoxyPaging = "com.airbnb.android:epoxy-paging:${Versions.epoxyVersion}"
    const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"

    const val junit = "junit:junit:4.13"
    const val assertjCore = "org.assertj:assertj-core:${Versions.assertJVersion}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTestingVersion}"
    const val testCore = "androidx.test:core:${Versions.androidxCoreVersion}"
    const val testExtJunit = "androidx.test.ext:junit:${Versions.androidxJunitVersion}"
    const val testRunner = "androidx.test:runner:${Versions.runnerVersion}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"

    debugImplementation "androidx.fragment:fragment-testing:$fragmentVersion"
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation "androidx.test.espresso:espresso-core:$espressoCoreVersion"
    androidTestImplementation "androidx.test.espresso:espresso-intents:$espressoCoreVersion"
    androidTestImplementation "com.google.dagger:dagger:$daggerAndroidVersion"
    androidTestImplementation "com.google.dagger:dagger-android-support:$daggerAndroidVersion"
    kaptAndroidTest "com.google.dagger:dagger-compiler:$daggerAndroidVersion"
    androidTestImplementation "com.agoda.kakao:kakao:$kakaoVersion"
    androidTestImplementation "com.squareup.okhttp3:mockwebserver:$mockWebServerVersion"
}