pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}


dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "scriptkt"

includeBuild("build-logic/dependencies")
includeBuild("build-logic/configuration")
includeBuild("build-logic/deploy")

include(":features:maven-resolver")
include(":features:import-script")