object Dependencies {
    object Project {
        const val applicationId = "com.breno.instagram"
        const val applicationNamespace = applicationId
        const val libraryNamespace = "com.breno.instagram"

        const val compileSdk = 33
        const val minSdk = 24
        const val targetSdk = 33

        const val applicationCode = 1
        const val applicationVersion = "1.0"
    }

    object Gradle {
        private const val version = "7.4.1"

        const val classpath = "com.android.tools.build:gradle:$version"
    }

    object Kotlin {
        private const val group = "org.jetbrains.kotlin"
        private const val version = "1.9.0"

        const val classpath = "$group:kotlin-gradle-plugin:$version"
        const val implementation = "$group:kotlin-stdlib:$version"

        object Coroutines {
            private const val version = "1.6.4"

            const val testImplementation = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Android {

        object Core {
            private const val group = "androidx.core"
            private const val version = "1.7.0"
            private const val versionArchCore = "2.2.0"

            const val implementation = "$group:core-ktx:$version"
            const val implementationArchCore = "androidx.arch.core:core-testing:$versionArchCore"

        }

        object Lifecycle {
            private const val group = "androidx.lifecycle"
            private const val version = "2.5.1"

            const val implementationViewModel = "$group:lifecycle-viewmodel-ktx:$version"
            const val implementationLiveData = "$group:lifecycle-livedata-ktx:$version"
        }

        object Navigation {
            private const val group = "androidx.navigation"
            private const val version = "2.5.3"

            const val implementationFragment = "$group:navigation-fragment-ktx:$version"
            const val implementationUi = "$group:navigation-ui-ktx:$version"
        }

        object AppCompat {
            private const val group = "androidx.appcompat"
            private const val version = "1.3.0"

            const val implementation = "$group:appcompat:$version"
        }

        object ConstraintLayout {
            private const val group = "androidx.constraintlayout"
            private const val version = "2.0.4"

            const val implementation = "$group:constraintlayout:$version"
        }

        object Camera {
            private const val group = "androidx.camera"
            private const val version = "1.0.1"
            private const val versionView = "1.0.0-alpha27"

            const val implementation = "$group:camera-camera2:$version"
            const val implementationLifecycle = "$group:camera-lifecycle:$version"
            const val implementationView = "$group:camera-view:$versionView"
        }
    }

    object Firebase {
        private const val group = "com.google.firebase"

        private const val versionBom = "32.4.0"

        const val implementationBom = "$group:firebase-bom:$versionBom"

        object Firestore {
            const val implementation = "$group:firebase-firestore-ktx"
        }
    }

    object Koin {
        private const val group = "io.insert-koin"
        private const val version = "3.3.2"

        private const val test = "$group:koin-test:$version"
        private const val testjunit4 = "$group:koin-test-junit4:$version"

        const val implementation = "$group:koin-android:$version"
        const val implementationCore = "$group:koin-core:$version"

    }

    object Retrofit {
        private const val group = "com.squareup.retrofit2"
        private const val version = "2.9.0"

        const val converterGson = "$group:converter-gson:$version"
        const val converterMoshi = "$group:converter-moshi:$version"
        const val implementation = "$group:retrofit:$version"
    }

    object OKHttp {
        private const val group = "com.squareup.okhttp3"
        private const val version = "3.12.3"

        const val okhttpLoggingInterceptor = "$group:logging-interceptor:$version"
        const val okhttpImplementation = "$group:okhttp:$version"
    }

    object Glide {
        private const val group = "com.github.bumptech.glide"
        private const val version = "4.11.0"

        const val implementation = "$group:glide:$version"

    }

    object CircleImageView {
        private const val group = "de.hdodenhof"
        private const val version = "3.1.0"

        const val implementation = "$group:circleimageview:$version"
    }

    object ImageCropper {
        private const val group = "com.theartofdev.edmodo"
        private const val version = "2.8.0"

        const val implementation = "$group:android-image-cropper:$version"

    }

    object Material {
        private const val group = "com.google.android.material"
        private const val version = "1.3.0"

        const val implementation = "$group:material:$version"
    }

    object Test {
        object Mockk {
            private const val group = "io.mockk"
            private const val version = "1.13.5"

            const val testImplementation = "$group:mockk:$version"
        }

        object Junit {
            private const val group = "junit"
            private const val version = "4.13.2"

            const val testImplementation = "$group:junit:$version"
        }
    }

    object AndroidTest {
        private const val group = "androidx.test"
        private const val version = "1.1.5"
        private const val versionEspresso = "3.5.1"

        const val testImplementation = "$group.ext:junit:$version"
        const val testImplementationExpresso = "$group.espresso:espresso-core:$versionEspresso"
    }
}