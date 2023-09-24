plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "ru.tanexc.notegraph"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.tanexc.notegraph"
        minSdk = 21
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // AndroidX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha12")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Compose
    implementation("androidx.compose.ui:ui:1.6.0-alpha06")
    implementation("androidx.compose.material3:material3:1.2.0-alpha08")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.0-alpha08")
    implementation("androidx.compose.material:material-icons-core:1.6.0-alpha06")
    implementation("androidx.compose.material:material-icons-extended:1.6.0-alpha06")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha06")
    implementation("androidx.compose.ui:ui-util:1.6.0-alpha06")
    implementation("androidx.compose.material:material:1.5.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    ksp("com.google.dagger:hilt-android-compiler:2.48")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-config-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Google Play auth
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //Dynamic theme
    implementation("com.github.t8rin:dynamictheme:1.0.3")

    //Free scroll
    implementation("com.github.chihsuanwu:compose-free-scroll:0.2.1")

    // ComposeShadowPlus
    implementation("com.github.GIGAMOLE:ComposeShadowsPlus:1.0.2")

    // Navigation
    implementation("dev.olshevski.navigation:reimagined:1.3.1")
    implementation("dev.olshevski.navigation:reimagined-hilt:1.3.1")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation("com.google.accompanist:accompanist-flowlayout:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-placeholder-material:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-pager:0.29.1-alpha")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.29.1-alpha")

}