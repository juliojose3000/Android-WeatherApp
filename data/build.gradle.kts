plugins {
    alias(libs.plugins.android.library)

    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.loaizasoftware.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.appcompat)
    implementation(libs.material)

    // ------------------------------------------------------
    // 🌐 NETWORKING
    // ------------------------------------------------------

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (for logging and customization)
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Gson for JSON parsing
    implementation("com.google.code.gson:gson:2.10.1")

    // ------------------------------------------------------
    // 🔐 DEPENDENCY INJECTION
    // ------------------------------------------------------

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    annotationProcessor("com.google.dagger:hilt-compiler:2.48")

    // ------------------------------------------------------
    // 💾 Local Storage
    // ------------------------------------------------------

    //Room
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}