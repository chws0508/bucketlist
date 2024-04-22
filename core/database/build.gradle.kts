plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.test.library")
    id("convention.coroutine")
    id("convention.android.hilt")
    kotlin("kapt")
}

android {
    namespace = "com.woosuk.database"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    testImplementation(libs.room.testing)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.room.testing)
}
