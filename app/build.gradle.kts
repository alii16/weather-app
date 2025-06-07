plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Tidak ada lagi baris untuk BuildConfigField di sini
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Material Design Components (untuk CardView, EditText, dll.)
    implementation ("com.google.android.material:material:1.12.0")

    // Retrofit (untuk HTTP requests)
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson Converter (untuk parsing JSON dengan Retrofit)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide (untuk memuat gambar/ikon, jika diperlukan)
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    // Default AndroidX dependencies
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Lottie Animation
    implementation ("com.airbnb.android:lottie:6.0.0")
}