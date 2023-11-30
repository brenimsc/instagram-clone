buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.classpath)
        classpath(Dependencies.Kotlin.classpath)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
        classpath("com.google.gms:google-services:4.4.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}