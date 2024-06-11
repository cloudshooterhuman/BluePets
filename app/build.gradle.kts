plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.cleancompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.cleancompose"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    @Suppress("UnstableApiUsage")
    buildFeatures {
        compose = true
    }

    @Suppress("UnstableApiUsage")
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

}

dependencies {


    implementation(libs.androidx.activity.compose)

    //Compose
    implementation (platform(libs.androidx.compose.bom))
    implementation (libs.ui)
    implementation (libs.material3)


    // Lifecycle utilities for Compose
    implementation(libs.lifecycle.runtime.compose)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.converter.gson)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.dagger.hilt.android.compiler)

    // UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.navigation.fragment.ktx)

    // Testing
    testImplementation("junit:junit:${Versions.JUNIT}")
    testImplementation("io.mockk:mockk:${Versions.MOCKK}")


    //Compose images loader
    implementation(libs.coil.compose)



}

object Versions {

    const val ANDROID_CORE = "1.7.0"
    const val APP_COMPAT = "1.6.0"
    const val GLIDE = "4.14.2"
    const val HILT = "2.48"
    const val MATERIAL = "1.7.0"
    const val NAVIGATION = "2.5.3"
    const val RETROFIT = "2.9.0"

    // Gradle
    const val ANDROID_PLUGIN = "7.4.2"
    const val KOTLIN = "1.9.24"

    // Testing
    const val JUNIT = "4.13.2"
    const val MOCKK = "1.13.3"
    const val COROUTINES_TEST = "1.6.4"

    // Compose
    const val COMPOSE_BOM = "2023.01.00"
    const val COIL = "2.2.2"

}