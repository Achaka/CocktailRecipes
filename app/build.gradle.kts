
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.compileSdk
    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(Dependencies.Room.room)
    implementation(Dependencies.Room.testImplementation)
    kapt(Dependencies.Room.kaptRoom)

    implementation(Dependencies.Coroutines.coroutines)

    implementation(platform(Dependencies.Okhttp.okhttpBom))
    implementation(Dependencies.Okhttp.okhttp)
    implementation(Dependencies.Okhttp.okhttpLoggingInterceptor)

    implementation(Dependencies.Retrofit.retrofit)

    implementation(Dependencies.Dagger2.daggerKapt)
    implementation(Dependencies.Dagger2.dagger)

    implementation (Dependencies.Android.coreKtx)
    implementation (Dependencies.Android.appCompat)
    implementation (Dependencies.Android.material)
    implementation (Dependencies.Android.constraintLayout)

    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}