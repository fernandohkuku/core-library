import com.android.build.gradle.BaseExtension
import com.android.build.gradle.api.AndroidBasePlugin
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.com.android.application).apply(false)
    alias(libs.plugins.org.jetbrains.kotlin.android).apply(false)
    alias(libs.plugins.com.android.library).apply(false)
    alias(libs.plugins.com.google.devtools.ksp).apply(false)
    alias(libs.plugins.com.google.dagger.hilt.android).apply(false)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
}

subprojects {
    afterEvaluate {
        plugins.withType<AndroidBasePlugin> {
            configure<BaseExtension> {
                configureProperties()
            }
        }
        dependencies {
            implementation(libs.androidx.corektx)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.compose.activity)
            implementation(libs.bundles.compose)

            // Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            // Hilt
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.hilt.android)

            // Moshi
            implementation(libs.moshi)
            implementation(libs.moshi.kotlin)

            // Retrofit/
            implementation(libs.retrofit2.converter.moshi)

            // /Test implementation
            testImplementation(libs.junit)
            testImplementation(libs.kotlinx.coroutines.test)
            testImplementation(libs.mockito.core)
            testImplementation(libs.mockk)
            testImplementation(libs.mockito.inline)


            // Android test implementation
            androidTestImplementation(libs.androidx.test.ext.junit)
            androidTestImplementation(libs.androidx.test.espresso.core)
            androidTestImplementation(platform(libs.androidx.compose.bom))
            androidTestImplementation(libs.androidx.compose.ui.test.junit4)


            // Debug Implementation
            debugImplementation(libs.androidx.compose.ui.tooling)
            debugImplementation(libs.androidx.compose.ui.test.manifest)
        }
    }
}

fun BaseExtension.configureProperties() {
    setCompileSdkVersion(34)
    defaultConfig {
        minSdk = 24
        targetSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += listOf(
                "-XXLanguage:-ProperCheckAnnotationsTargetInTypeUsePositions"
            )
        }
    }

    buildFeatures.apply {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

fun DependencyHandlerScope.androidTestImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", library)
}

fun DependencyHandlerScope.debugImplementation(library: LibrariesForLibs.AndroidxComposeUiToolingLibraryAccessors) {
    add("debugImplementation", library)
}

fun DependencyHandlerScope.debugImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", library)
}

tasks.clean.apply {
    delete(rootProject.buildDir)
}
