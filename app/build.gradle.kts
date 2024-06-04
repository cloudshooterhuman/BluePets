import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
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

        val apiKey: String = Properties().apply {
            load(FileInputStream("api.properties"))
        }["APP_ID"] as String
        buildConfigField("String", "API_KEY", apiKey)


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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("javax.inject:javax.inject:1")

    // Retrofit
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)



}

object Versions {

    const val ANDROID_CORE = "1.7.0"
    const val APP_COMPAT = "1.6.0"
    const val GLIDE = "4.14.2"
    const val HILT = "2.42"
    const val MATERIAL = "1.7.0"
    const val NAVIGATION = "2.5.3"
    const val RETROFIT = "2.9.0"

    // Gradle
    const val ANDROID_PLUGIN = "7.4.2"
    const val KOTLIN = "1.8.10"

    // Testing
    const val JUNIT = "4.13.2"
    const val MOCKK = "1.13.3"
    const val COROUTINES_TEST = "1.6.4"

    // Compose
    const val COMPOSE_BOM = "2023.01.00"
    const val COIL = "2.2.2"

}