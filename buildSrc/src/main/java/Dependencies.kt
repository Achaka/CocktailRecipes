object Dependencies {

    object Room {
        private const val version = "2.3.0"
        const val room = "androidx.room:room-runtime:$version"
        const val kaptRoom = "androidx.room:room-compiler:$version"

        const val roomCoroutinesExtensions = "androidx.room:room-ktx:$version"
        const val roomRxJava3 = "androidx.room:room-rxjava3:$version"

        // optional - Test helpers
        const val testImplementation = "androidx.room:room-testing:$version"

//        // optional - Paging 3 Integration
//        const val paging = "androidx.room:room-paging:2.4.0-beta02"
    }

    object Retrofit {
        private const val version = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    }

    object Okhttp {
        private const val version = "4.9.0"

        const val okhttp = "com.squareup.okhttp3:okhttp"
        const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor"
        const val okhttpBom = "com.squareup.okhttp3:okhttp-bom:$version"
    }

    object Dagger2 {
        private const val version = "2.40.1"

        const val dagger = "com.google.dagger:dagger:$version"
        const val daggerKapt = "com.google.dagger:dagger-compiler:$version"
    }

    object RxJava3 {
        private const val version = "3.0.0"

        const val rxandroid = "io.reactivex.rxjava3:rxandroid:$version"
        const val rxjava = "io.reactivex.rxjava3:rxjava:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"

        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.6.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val material = "com.google.android.material:material:1.4.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.2"
    }

    object Glide {
        private const val version = "4.12.0"

        const val glide = "com.github.bumptech.glide:glide:$version"
    }

    object ViewBindingDelegate {
        private const val version = "1.5.2"

        const val viewBindingDelegate = "com.github.kirich1409:viewbindingpropertydelegate:$version"
    }
}