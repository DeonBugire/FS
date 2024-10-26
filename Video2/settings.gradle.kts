pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Video2"
include(":app")
include(":core")
include(":movielist")
include(":moviedetails")
include(":favorites")
include(":favorites")
include(":favorites:data")
include(":favorites:domain")
include(":favorites:di")
include(":moviedetails:domain")
include(":moviedetails:data")
include(":moviedetails:presentation")
include(":moviedetails:di")
include(":movielist:data")
include(":movielist:di")
include(":movielist:presentation")
include(":movielist:domain")
