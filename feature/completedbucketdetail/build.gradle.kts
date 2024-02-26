plugins {
    id("convention.feature")
}

android {
    namespace = "com.woosuk.completedbucketdetail"
}
dependencies{
    implementation(project(":feature:completebucket"))
}
