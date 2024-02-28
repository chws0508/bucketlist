plugins {
    alias(libs.plugins.android.library)
    id("convention.android.compose")
    id("convention.test.library")
}

android {
    namespace = "com.woosuk.designsystem"
}
