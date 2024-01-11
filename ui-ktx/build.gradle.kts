plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.homedepot.sa.xp.logprocessor.ui_ktx"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {

        }
        create("qa") {

        }
    }
}

dependencies {

}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "com.github.fernandohkuku"
                artifactId = "ui-ktx-preview-test"
                version = "1.0"
            }
        }
    }
}