plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    //alias(libs.plugins.com.google.dagger.hilt.android)
}

android {
    namespace = "com.homedepot.sa.xp.logprocessor"

    defaultConfig {
        applicationId = "com.homedepot.sa.xp.logprocessor"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("qa") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = true
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    //Library core ktx
    implementation(projects.logs)
    implementation(projects.core)
    //Library ui ktx
    implementation(projects.uiKtx)
    //System UI controller
    implementation(libs.accompanist.systemuicontroller)
    //Splash Screen API
    implementation(libs.androidx.core.splashscreen)
    //Event Logger
   // implementation("com.homedepot.sa.xp:event-logger:1.0.29@aar")
    //Dagger hilt compiler
    //ksp(libs.hilt.android.compiler)
}