plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.android.hilt")
    id("convention.coroutine")
    id("convention.test.library")
}

android {
    namespace = "com.woosuk.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}
