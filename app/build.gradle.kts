plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.demoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.demoapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation (libs.lottie)
    implementation (platform(libs.firebase.bom.v3100))
    implementation (libs.firebase.auth)
    implementation (libs.browser) //hel
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation ("com.google.android.material:material:v160")
    implementation ("com.github.Drjacky:ImagePicker:2.3.22")
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation("com.android.support:multidex:1.0.3")
    implementation(libs.firebase.ui.firestore)
    implementation("com.firebaseui:firebase-ui-storage:8.0.2")
    implementation(libs.firebase.ui.database)
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}