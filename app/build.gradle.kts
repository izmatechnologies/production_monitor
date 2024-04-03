plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")

}

android {
    namespace = "com.rmg.production_monitor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.rmg.production_monitor"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding=true
        buildConfig= true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    //image downloader
    implementation ("io.coil-kt:coil-compose:2.4.0")
    val hilt_version = "2.44"
    //noinspection GradleDependency
    implementation ("com.google.dagger:hilt-android:$hilt_version")
    ksp ("com.google.dagger:hilt-compiler:$hilt_version")

    //viewmodel
    val viewmodelVersion = "2.6.1"
    //noinspection GradleDependency
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewmodelVersion")
    //lifecycle and viewModel
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    //coroutines
    val coroutines_version = "1.6.0"
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    // paging
    val paging_version = "3.2.1"
    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")

    val retrofit_version = "2.9.0"
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")
    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    implementation ("com.squareup.okhttp3:logging-interceptor")
    // glide for image load
    implementation ("com.github.bumptech.glide:glide:4.16.0")


    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    // Room
    val room_version = "2.6.1"
    implementation ("androidx.room:room-runtime:$room_version")
    ksp ("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    // implementation "androidx.room:room-paging:$room_version"

//    val lifecycleVersion = "2.7.0"
//    // ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
//    // ViewModel utilities for Compose
//    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
//    // LiveData
//    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
//    // Lifecycles only (without ViewModel or LiveData)
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
//    // Lifecycle utilities for Compose
//    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")
//    // Saved state module for ViewModel
//    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")
//    // Lifecycle dependencies with Java 8 API
//    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
//
//    // coroutines
//    val coroutinesVersion = "1.7.3"
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
//
//    val navigationVersion = "2.7.7"
//
//    // Navigation Component
//    implementation ("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
//    implementation ("androidx.navigation:navigation-ui-ktx:$navigationVersion")
//
//    //Hilt Android
//    val hiltAndroidVersion = "2.50"
//    implementation ("com.google.dagger:hilt-android:$hiltAndroidVersion")
//    implementation ("com.google.dagger:hilt-android-compiler:$hiltAndroidVersion")
//    //Hilt Compiler
//    val hiltCompilerVersion = "1.2.0"
//    implementation ("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
//
//    // Retrofit
//    val retrofit2Version = "2.9.0"
//    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
//    // Gson converter
//    implementation("com.squareup.retrofit2:converter-gson:$retrofit2Version")
//    // interceptor
//
//    val loggingInterceptorVersion = "4.12.0"
//    implementation("com.squareup.okhttp3:logging-interceptor:$loggingInterceptorVersion")
}