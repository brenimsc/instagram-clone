plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = Dependencies.Project.applicationNamespace
    compileSdk = Dependencies.Project.compileSdk

    defaultConfig {
        applicationId = Dependencies.Project.applicationId
        minSdk = Dependencies.Project.minSdk
        targetSdk = Dependencies.Project.targetSdk
        versionCode = Dependencies.Project.applicationCode
        versionName = Dependencies.Project.applicationVersion

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    /* Kotlin */
    //implementation(Dependencies.Kotlin.implementation)

    /* Android */
    implementation(Dependencies.Android.Lifecycle.implementationViewModel)
    implementation(Dependencies.Android.Lifecycle.implementationLiveData)

    implementation(Dependencies.Glide.implementation)

    implementation(Dependencies.Retrofit.converterGson)
    implementation(Dependencies.Retrofit.converterMoshi)
    implementation(Dependencies.Retrofit.implementation)
    implementation(Dependencies.OKHttp.okhttpLoggingInterceptor)
    implementation(Dependencies.OKHttp.okhttpImplementation)

    implementation(Dependencies.Koin.implementation)
    implementation(Dependencies.Koin.implementationCore)

    implementation(Dependencies.Android.Core.implementation)
    implementation(Dependencies.Android.AppCompat.implementation)
    implementation(Dependencies.Material.implementation)
    implementation(Dependencies.Android.ConstraintLayout.implementation)
    implementation(Dependencies.Android.Navigation.implementationFragment)
    implementation(Dependencies.Android.Navigation.implementationUi)
    implementation(Dependencies.Android.Camera.implementation)
    implementation(Dependencies.Android.Camera.implementationLifecycle)
    implementation(Dependencies.Android.Camera.implementationView)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(Dependencies.CircleImageView.implementation)
    implementation(Dependencies.ImageCropper.implementation)
    implementation(platform(Dependencies.Firebase.implementationBom))
    implementation(Dependencies.Firebase.Firestore.implementation)
    implementation("com.google.firebase:firebase-auth-ktx:22.2.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")

    testImplementation(Dependencies.Test.Junit.testImplementation)
    testImplementation(Dependencies.Test.Mockk.testImplementation)
    testImplementation(Dependencies.Android.Core.implementationArchCore)
    testImplementation(Dependencies.Kotlin.Coroutines.testImplementation)
    androidTestImplementation(Dependencies.AndroidTest.testImplementation)
    androidTestImplementation(Dependencies.AndroidTest.testImplementationExpresso)
}