plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.android.developer.prof.reda.voyagex"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.developer.prof.reda.voyagex"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        dataBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("androidx.test.ext:junit-ktx:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-auth:22.3.1")

    //Navigation component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.2")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.2")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")

    //Android Ktx
    implementation ("androidx.navigation:navigation-fragment-ktx:2.4.2")

    //Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-compiler:2.50")

    //circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // Logging
    implementation ("com.jakewharton.timber:timber:5.0.1")

    //Coroutines with firebase
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1")

    //splash screen
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // JUnit and Mockito for unit testing
    testImplementation ("org.mockito:mockito-core:4.0.0")
    testImplementation ("org.mockito:mockito-inline:3.9.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")


    //Coroutines test
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //Turbine
    testImplementation ("app.cash.turbine:turbine:0.7.0")

    // Hilt testing utilities
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.44")

    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.44")
}
kapt{
    correctErrorTypes = true
}