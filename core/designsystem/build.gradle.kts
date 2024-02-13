plugins {
    id("com.android.library")
    id("convention.android.compose")
    id("convention.test.library")
}

android {
    namespace = "com.woosuk.designsystem"
}

dependencies {
    // Splash API
    implementation(libs.androidx.core.splashscreen)
}
