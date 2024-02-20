plugins {
    id("convention.data")
    kotlin("kapt")
}

android {
    namespace = "com.woosuk.database"
}

dependencies {
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    testImplementation(libs.room.testing)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.room.testing)
}
