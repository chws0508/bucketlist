pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "bucketlist"
include(":app")
include(":core:domain")
include(":core:designsystem")
include(":core:database")
include(":core:testing")
include(":core:data")
include(":core:common")
include(":feature:main")
include(":feature:home")
include(":feature:addbucket")
include(":feature:completebucket")
include(":feature:updatecompletedbucket")
include(":feature:completedbucketdetail")
