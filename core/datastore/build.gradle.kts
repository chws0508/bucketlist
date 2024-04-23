plugins {
    id("com.android.library")
    id("convention.android.base")
    id("convention.test.library")
    id("convention.coroutine")
    id("convention.android.hilt")
    alias(libs.plugins.protobuf)
}

android {
    namespace = "com.woosuk.datastore"
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                register("java") {
                    option("lite")
                }
                register("kotlin"){
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.androidx.datastore)
    implementation(libs.protobuf.kotlin.lite)
}
