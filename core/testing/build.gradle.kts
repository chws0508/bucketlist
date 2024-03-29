plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.coroutine")
    id("convention.test.library")
}

android {
    namespace = "com.woosuk.testing"
}

dependencies {
    api(libs.coroutines.test)
    api(libs.junit4)
    api(libs.mockk)
}
