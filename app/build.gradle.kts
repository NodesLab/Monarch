plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

val composeVersion = "1.1.0-beta01"

android {
  compileSdk = 32

  defaultConfig {
    applicationId = "me.hepller.helioapp"
    minSdk = 31
    targetSdk = 32
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    vectorDrawables {
      useSupportLibrary = true
    }
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

  kotlinOptions {
    jvmTarget = "11"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = composeVersion
  }

  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("androidx.compose.ui:ui:${composeVersion}")
  implementation("androidx.compose.material:material:${composeVersion}")
  implementation("androidx.compose.ui:ui-tooling-preview:${composeVersion}")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
  implementation("androidx.activity:activity-compose:1.3.1")
  implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")

  testImplementation("junit:junit:4.13.2")

  androidTestImplementation("androidx.test.ext:junit:1.1.3")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:${composeVersion}")

  debugImplementation("androidx.compose.ui:ui-tooling:${composeVersion}")
  debugImplementation("androidx.compose.ui:ui-test-manifest:${composeVersion}")
}