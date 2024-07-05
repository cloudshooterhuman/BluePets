plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.cleancompose"
    compileSdk = AndroidOptions.COMPILE_SDK

    defaultConfig {
        applicationId = "com.cleancompose"
        minSdk = AndroidOptions.MIN_SDK
        targetSdk = AndroidOptions.COMPILE_SDK
        versionCode = AppVersions.VERSION_CODE
        versionName = AppVersions.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.ui.tooling.preview)
    implementation(project(":api"))
    debugImplementation(libs.ui.tooling)

    // Lifecycle utilities for Compose
    implementation(libs.lifecycle.runtime.compose)

    // Paging 3
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Retrofit
    api(libs.retrofit)
    implementation(libs.converter.gson)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview.android)
    kapt(libs.dagger.hilt.android.compiler)

    // UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.navigation.fragment.ktx)

    // Compose images loader
    implementation(libs.coil.compose)

    // Logging
    implementation(libs.timber)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.paging.testing)

    // UI Testing
    // implementation(libs.androidx.paging.testing)
}
