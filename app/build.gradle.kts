
plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id ("com.github.ben-manes.versions")
    id ("com.google.devtools.ksp")
}

android {
    compileSdk = AppConfig.compileSdkVersion

    defaultConfig {
        applicationId ="com.akram.bensalem.powersh"
        minSdk = AppConfig.minSdkVersion
        targetSdk = AppConfig.targetSdkVersion
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xopt-in=kotlin.RequiresOptIn"
        )
    }


    packagingOptions {
        resources {
            excludes += mutableSetOf(
                "/META-INF/{AL2.0,LGPL2.1}"
            )
        }
    }
}

dependencies {

    implementation(Deps.coreKtx)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.lifecycleRuntimeKtx)
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-firestore-ktx:23.0.3")

    testImplementation(Deps.junit)
    androidTestImplementation(Deps.junitTest)
    androidTestImplementation(Deps.espressoCore)

    // Testing Compose
    androidTestImplementation(Deps.junitCompose)
    debugImplementation(Deps.composeTooling)

    // Compose
    implementation(Deps.composeUi)
    implementation(Deps.composeAnimation)
    implementation(Deps.composeMaterial)
    implementation(Deps.composePreview)
    implementation(Deps.composeActivity)
    implementation(Deps.composeViewModel)
    implementation(Deps.composeNavigation)
    implementation(Deps.composeMaterialIconsCore)
    implementation(Deps.composeMaterialIconsExtended)
    implementation(Deps.composeFoundation)
    implementation(Deps.composeFoundationLayout)
    implementation(Deps.composeConstraintLayout)

    // Retrofit
    implementation(Deps.retrofit)

    // Retrosheet
    implementation(Deps.retrosheet)

    // Moshi
    implementation(Deps.moshi)
    implementation(Deps.moshiKotlin)

    // Timber
    implementation(Deps.timber)

    // Hilt
    implementation(Deps.hilt)
    implementation(Deps.hiltNavigationCompose)
    kapt(Deps.hiltCompiler)

    // Coil Image loader
    implementation(Deps.coilImage)

    // Accompanist
    implementation(Deps.accompanistInsets)
    implementation(Deps.accompanistNavigationAnimations)
    implementation(Deps.accompanistPager)
    implementation(Deps.accompanistSwiperefresh)
    implementation(Deps.accompanistPagerIndication)
    implementation(Deps.accompanistPermissions)

    
    // Room database
    implementation(Deps.roomRuntime)
    implementation(Deps.roomKtx)
    kapt(Deps.roomCompiler)

    // Room test
    testImplementation(Deps.roomTest)

    // Preferences DataStore
    implementation(Deps.prefDataStore)

    // Splash Screen
    implementation(Deps.splashScreenCore)


    implementation(Deps.lottiesAnim)

    implementation(Deps.ballonAnchor)

    implementation(Deps.gson)

    /*implementation(Deps.android_mail)
    implementation(Deps.android_activation)*/


    implementation("com.google.devtools.ksp:symbol-processing-api:1.5.30-1.0.0-beta09")

    // Required
    implementation (Deps.lyricist)

    compileOnly ("cafe.adriel.lyricist:lyricist-processor:1.0.0")
    ksp ("cafe.adriel.lyricist:lyricist-processor:1.0.0")


    implementation ("io.reactivex.rxjava3:rxandroid:3.0.0")
    implementation ("io.reactivex.rxjava3:rxjava:3.0.0")


    implementation (files("libs/mail.jar"))
    implementation (files("libs/activation.jar"))
    implementation (files("libs/additionnal.jar"))



    implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
    implementation("com.google.maps.android:maps-v3-ktx:2.2.0")
    implementation("androidx.fragment:fragment:1.3.6")

    implementation("com.android.volley:volley:1.2.1")
    implementation("com.itextpdf:itext7-core:7.1.11")

}