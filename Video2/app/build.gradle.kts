plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.example.video"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.video"
        minSdk = 24
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":moviedetails:presentation"))
    implementation(project(":moviedetails:data"))
    implementation(project(":moviedetails:domain"))

    implementation(project(":movielist:presentation"))
    implementation(project(":movielist:data"))
    implementation(project(":movielist:domain"))

    implementation (libs.dagger)
    implementation(project(":favorites:di"))
    implementation(project(":favorites:domain"))
    implementation(project(":favorites:data"))
    kapt (libs.dagger.compiler)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)

    implementation (libs.androidx.datastore.preferences)
    implementation (libs.androidx.datastore)
}