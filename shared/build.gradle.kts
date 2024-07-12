import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.jetbrains.kotlinMultiplatform)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.kotlinPluginSerialization)
    alias(libs.plugins.jetbrains.kotlinCocoapods)
    alias(libs.plugins.icerock.mokoMobileMultiplatformResources)
    alias(libs.plugins.rickclephas.kmp.nativecoroutines)
}

kotlin {

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        languageSettings.optIn("kotlin.experimental.ExperimentalMultiplatform")
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true

            // Decompose
            export(libs.arkivanov.decompose)
            export(libs.arkivanov.essenty.lifecycle)
            export(libs.arkivanov.essenty.stateKeeper)

            // MVI
            export(libs.arkivanov.mvikotlin)
            export(libs.arkivanov.mvikotlinMain)
            export(libs.arkivanov.mvikotlinExtensionsCoroutines)
            export(libs.arkivanov.mvikotlinLogger)
            export(libs.arkivanov.mvikotlinTimetravel)

            // Ktor
            export(libs.ktor.client.darwin)

            // Resource
            export(libs.moko.resources)
            export(libs.moko.graphics)

//            pod("MCRCDynamicProxy","1.0.0-ALPHA-31")
//            pod("KMPNativeCoroutinesCombine", "1.0.0-ALPHA-31")
//            pod("KMPNativeCoroutinesRxSwift", "1.0.0-ALPHA-31")

        }
    }

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }
    }

}

tasks.withType<KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

dependencies {

    add("kspCommonMainMetadata", libs.arrow.ksp)

    // Compose Multiplatform
    commonMainImplementation(libs.jetbrains.composeRuntime)
    commonMainImplementation(libs.jetbrains.composeFoundation)
    commonMainImplementation(libs.jetbrains.composeMaterial)
    commonMainImplementation(libs.jetbrains.composeUi)

    // Jetbrains
    commonMainApi(libs.jetbrains.kotlinx.coroutines.core)
    commonMainApi(libs.jetbrains.kotlinxCollectionsImmutable)
    commonMainApi(libs.jetbrains.kotlinx.serialization.json)
    commonMainApi(libs.ktor.client.core)

    // Decompose
    commonMainApi(libs.arkivanov.decompose)
    commonMainApi(libs.arkivanov.decompose.extensionsCompose)

    // MVI
    commonMainApi(libs.arkivanov.mvikotlin)
    commonMainApi(libs.arkivanov.mvikotlinMain)
    commonMainApi(libs.arkivanov.mvikotlinExtensionsCoroutines)
    commonMainApi(libs.arkivanov.mvikotlinLogger)
    commonMainApi(libs.arkivanov.mvikotlinTimetravel)

    // Arrow
    commonMainApi(libs.arrow.core)
    commonMainApi(libs.arrow.fx.coroutines)
    commonMainApi(libs.arrow.optics)

    // Koin
    commonMainApi(libs.koin.core)
    commonMainApi(libs.koin.compose)

    // Moko
    commonMainApi(libs.moko.resources)
    commonMainApi(libs.moko.resourcesCompose)

    "androidMainApi"(libs.androidx.camera.core)

    // Jetbrains
    "androidMainApi"(libs.jetbrains.kotlinx.coroutines.core)
    "androidMainApi"(libs.jetbrains.kotlinx.coroutines.android)
    "androidMainApi"(libs.ktor.client.okhttp)

    // Koin
    "androidMainApi"(libs.koin.android)

    "androidMainImplementation"(libs.compose.ui.tooling.preview)
}

android {
    namespace = "com.determent.ecombebop_management"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.version.get()
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

multiplatformResources {
    resourcesPackage.set("org.example.library")
}