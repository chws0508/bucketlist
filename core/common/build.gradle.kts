plugins {
    id("com.android.library")
    id("convention.android.compose")
    id("convention.test.library")
}

android {
    namespace = "com.woosuk.common"
}

dependencies {
    implementation(project(":core:domain"))
}
