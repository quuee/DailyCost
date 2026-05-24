plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

//    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "cn.x.dailycost"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "cn.x.dailycost"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.runtime)

    // Compose BOM 管理版本
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)

    // Compose 预览和调试
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // Navigation3
    implementation(libs.androidx.navigation.compose)

    // Koin DI
    implementation(libs.bundles.koin)

    // Network
    implementation(libs.bundles.network)

    // Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler) // 强依赖 KSP 插件

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Serialization
    //    implementation(libs.kotlinx.serialization.json)

    // Coil 图片加载
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Permissions
//    implementation(libs.accompanist.permissions)

    //    implementation("androidx.camera:camera-camera2:1.6.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}