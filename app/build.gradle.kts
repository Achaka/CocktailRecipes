plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
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
    implementation(Dependencies.Room.roomRxJava3)
    implementation(Dependencies.Room.roomCoroutinesExtensions)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Dependencies.Room.kaptRoom)

    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-android-compiler:2.38.1")

    implementation(Dependencies.Coroutines.coroutines)

    implementation(Dependencies.RxJava3.rxandroid)
    implementation(Dependencies.RxJava3.rxjava)

    implementation(platform(Dependencies.Okhttp.okhttpBom))
    implementation(Dependencies.Okhttp.okhttp)
    implementation(Dependencies.Okhttp.okhttpLoggingInterceptor)

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.rxjava3Adapter)
    implementation(Dependencies.Retrofit.moshiConverter)
//coroutines adapter -built in

    implementation(Dependencies.Moshi.moshi)
    implementation(Dependencies.Moshi.moshiKotlin)

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.viewModel)
    implementation(Dependencies.Android.fragment)

    implementation(Dependencies.Glide.glide)
//    implementation("androidx.core:core-splashscreen:1.0.0-alpha02")
    implementation(Dependencies.ViewBindingDelegate.viewBindingDelegate)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}