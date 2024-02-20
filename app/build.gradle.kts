plugins {
    id("convention.application")
}

android {
    namespace = "com.woosuk.bucketlist"

    defaultConfig {
        applicationId = "com.woosuk.bucketlist"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:main"))
    implementation(project(":feature:home"))
    implementation(project(":core:data"))
    implementation(libs.multidex)
}
kapt {
    correctErrorTypes = true
}
