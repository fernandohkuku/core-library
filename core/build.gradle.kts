import com.android.utils.minimumSizeOfTokenizeCommandLineBuffer

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    `maven-publish`
}

android {
    namespace = "com.homedepot.sa.xp.logprocessor.core"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {

        }
        create("qa") {

        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


dependencies {
    implementation(projects.uiKtx)
    //Data Store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.work.runtime.ktx)
    ksp(libs.hilt.android.compiler)
    ksp(libs.moshi.kotlin.codegen)
    //Rom database
    ksp(libs.androidx.room.compiler)

}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.fernandohkuku"
                artifactId = "core-preview-test"
                version = "1.0"

            }
        }
    }
}