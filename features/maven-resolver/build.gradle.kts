import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Deploy.Id)
}

dependencies {
    implementation(project(":"))
    testImplementation(Deps.Libs.Kotlin.Script.JvmHost)
    implementation(Deps.Libs.Kotlin.Script.Dependencies)
    implementation(Deps.Libs.Kotlin.Script.DependenciesMaven)
    implementation(Deps.Libs.Kotlinx.Coroutines)
    implementation(Deps.Libs.Kotlin.Script.Jvm)
    implementation(Deps.Libs.Kotlin.Test.JUnit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val deployProperties = rootProject.file("deploy.properties")

deploy {
    if (!deployProperties.exists())
        ignore = true
    else {
        val properties = loadProperties(deployProperties.absolutePath)
        host = properties["host"] as String?
        user = properties["user"] as String?
        password = properties["password"] as String?
        deployPath = properties["deployPath"] as String?

        componentName = "kotlin"
        group = AppInfo.PACKAGE
        version = AppInfo.VERSION
        artifactId = "maven-resolver"
        name = "script.kt"
        description = "Convenient Kotlin Script wrapper"
    }
}