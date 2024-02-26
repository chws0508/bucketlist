plugins {
    id("convention.feature")
}

android {
    namespace = "com.woosuk.main"
}
dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:addbucket"))
    implementation(project(":feature:completebucket"))
    implementation(project(":feature:updatecompletedbucket"))
    implementation(project(":feature:completedbucketdetail"))
}
