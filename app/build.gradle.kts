plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.taskbiddex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.biddex.taskbiddex"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)
    // SDP Library
    implementation(libs.sdp)
    // SSP Library
    implementation(libs.ssp)
    //RetroFit related libraries
    implementation(libs.okhttp3)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    // viewmodel lifecycle
    implementation (libs.lifecycle.viewmodel.ktx)
    // Glide
    implementation (libs.glide)
    implementation (libs.glide.compiler)
    //shimmer
    implementation(libs.shimmer)
    //swipe to refresh
    implementation(libs.swipe.refresh)

}