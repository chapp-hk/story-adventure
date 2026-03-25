plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    
    // iOS target - requires macOS to build
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    // Web target
    js {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(platform("androidx.compose:compose-bom:2024.02.00"))
                implementation("androidx.compose.runtime:runtime")
                implementation("androidx.compose.ui:ui")
                implementation("androidx.compose.foundation:foundation")
                implementation("androidx.compose.material3:material3")
                
                // Serialization for story JSON
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                
                // Coroutines for async storage
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.activity:activity-compose:1.8.2")
            }
        }
    }
}

android {
    namespace = "com.storyadventure.app"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.storyadventure.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    buildFeatures {
        compose = true
    }
}