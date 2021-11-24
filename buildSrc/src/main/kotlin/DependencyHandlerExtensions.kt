import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.add
import org.gradle.kotlin.dsl.exclude

private const val IMPLEMENTATION = "implementation"
private const val DEBUG_IMPLEMENTATION = "debugImplementation"
private const val RELEASE_IMPLEMENTATION = "releaseImplementation"
private const val KAPT = "kapt"
private const val KAPT_RELEASE = "kaptRelease"
private const val KAPT_DEBUG = "kaptDebug"
private const val COMPILE_ONLY = "compileOnly"
private const val TEST_IMPLEMENTATION = "testImplementation"
private const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
private const val KAPT_TEST = "kaptTest"
private const val KAPT_ANDROID_TEST = "kaptAndroidTest"
private const val ANDROID_TEST_UTIL = "androidTestUtil"

fun DependencyHandler.implementationWithTransitive(dependencyName: String, _isTransitive: Boolean = false) {
    add(IMPLEMENTATION, dependencyName) {
        isTransitive = _isTransitive
    }
}

fun DependencyHandler.compileOnly(dependencyName: String) {
    add(COMPILE_ONLY, dependencyName)
}

fun DependencyHandler.implementation(dependencyName: String) {
    add(IMPLEMENTATION, dependencyName)
}

fun DependencyHandler.debugImplementation(dependencyName: String) {
    add(DEBUG_IMPLEMENTATION, dependencyName)
}

fun DependencyHandler.releaseImplementation(dependencyName: String) {
    add(RELEASE_IMPLEMENTATION, dependencyName)
}

fun DependencyHandler.kapt(dependencyName: String) {
    add(KAPT, dependencyName)
}

fun DependencyHandler.implementationWithExcludes(dependencyName: String, group: String, module: String) {
    add(IMPLEMENTATION, dependencyName) {
        exclude(group = group, module = module)
    }
}

fun DependencyHandler.kaptRelease(dependencyName: String) {
    add(KAPT_RELEASE, dependencyName)
}

fun DependencyHandler.kaptDebug(dependencyName: String) {
    add(KAPT_DEBUG, dependencyName)
}

fun DependencyHandler.androidTestImplementation(dependencyName: String) {
    add(ANDROID_TEST_IMPLEMENTATION, dependencyName)
}

fun DependencyHandler.testImplementation(dependencyName: String) {
    add(TEST_IMPLEMENTATION, dependencyName)
}

fun DependencyHandler.kaptTest(dependencyName: String) {
    add(KAPT_TEST, dependencyName)
}

fun DependencyHandler.kaptAndroidTest(dependencyName: String) {
    add(KAPT_ANDROID_TEST, dependencyName)
}

fun DependencyHandler.androidTestUtil(dependencyName: String) {
    add(ANDROID_TEST_UTIL, dependencyName)
}