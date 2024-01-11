@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    id("maven-publish")
}

android {
    namespace = "com.homedepot.sa.xp.logprocessor.core"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}


dependencies {
    implementation(projects.uiKtx)
    //Data Store
    api(libs.androidx.datastore.preferences)
    //Dagger hilt compiler

    // Moshi
    api(libs.moshi)
    api(libs.moshi.kotlin)
    // Retrofit/
    api(libs.retrofit2.converter.moshi)
    //Hilt
    ksp(libs.hilt.android.compiler)
    ksp(libs.moshi.kotlin.codegen)
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