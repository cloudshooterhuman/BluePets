plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.cleancompose"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.cleancompose"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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


    implementation("androidx.activity:activity-compose:${Versions.APP_COMPAT}")

    //Compose
    implementation (platform("androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"))
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.material3:material3")



    // Retrofit
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}")

    // Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")

    // UI
    implementation("androidx.core:core-ktx:${Versions.ANDROID_CORE}")
    implementation("com.google.android.material:material:${Versions.MATERIAL}")
    // Navigation
    implementation("androidx.navigation:navigation-compose:${Versions.NAVIGATION}")
    implementation("androidx.navigation:navigation-runtime-ktx:${Versions.NAVIGATION}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}")

    // Testing
    testImplementation("junit:junit:${Versions.JUNIT}")
    testImplementation("io.mockk:mockk:${Versions.MOCKK}")


    //Compose images loader
    implementation("io.coil-kt:coil-compose:${Versions.COIL}")



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