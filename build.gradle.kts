plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.google.ksp).apply(false)
    alias(libs.plugins.jetbrains.kotlinAndroid).apply(false)
    alias(libs.plugins.jetbrains.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrains.compose.compiler).apply(false)
    alias(libs.plugins.jetbrains.kotlinCocoapods).apply(false)
    alias(libs.plugins.jetbrains.kotlinPluginSerialization).apply(false)
    alias(libs.plugins.icerock.mokoMobileMultiplatformResources).apply(false)
    alias(libs.plugins.rickclephas.kmp.nativecoroutines).apply(false)
}

buildscript {
    dependencies {
        classpath(libs.moko.resourcesGenerator)
    }
}