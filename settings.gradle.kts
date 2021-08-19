dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        //jcenter() // Warning: this repository is going to shut down soon
    }
}
rootProject.name = "ThinkRchive"
include(":app")
