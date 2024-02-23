plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.test.library")
    id("convention.coroutine")
    id("convention.android.compose")
    id("convention.android.hilt")
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:testing"))
    implementation(project(":core:common"))
}
