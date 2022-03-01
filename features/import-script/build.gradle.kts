plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Deploy.Id)
}

dependencies {
    implementation(project(":"))
    implementation(Deps.Libs.Kotlinx.Coroutines)
    implementation(Deps.Libs.Kotlin.Script.Jvm)
}

val deployProperties = rootProject.file("deploy.properties")

deploy {
    if (!deployProperties.exists())
        ignore = true
    else {
        val properties = org.jetbrains.kotlin.konan.properties.loadProperties(deployProperties.absolutePath)
        host = properties["host"] as String?
        user = properties["user"] as String?
        password = properties["password"] as String?
        deployPath = properties["deployPath"] as String?

        componentName = "kotlin"
        group = AppInfo.PACKAGE
        version = AppInfo.VERSION
        artifactId = "import-script"
        name = "script.kt"
        description = "Convenient Kotlin Script wrapper"
    }
}
