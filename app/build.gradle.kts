plugins {
    alias(libs.plugins.android.application)
    // If you use Kotlin, add this line:
    // alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.news_screen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.news_screen"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    // Uncomment if using Kotlin
    // kotlinOptions {
    //     jvmTarget = "11"
    // }
}

dependencies {
    // Core AndroidX and Material Components
    implementation(libs.appcompat)          // androidx.appcompat:appcompat
    implementation(libs.material)           // com.google.android.material:material
    implementation(libs.activity)           // androidx.activity:activity-ktx or activity
    implementation(libs.constraintlayout)  // androidx.constraintlayout:constraintlayout

    // ViewPager (if used)
    implementation(libs.viewpager)          // androidx.viewpager:viewpager

    // Testing libraries
    testImplementation(libs.junit)          // junit:junit
    androidTestImplementation(libs.ext.junit)      // androidx.test.ext:junit
    androidTestImplementation(libs.espresso.core)  // androidx.test.espresso:espresso-core
}
