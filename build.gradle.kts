import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id(Deps.Plugins.Configuration.Kotlin.Jvm)
    id(Deps.Plugins.Deploy.Id)
}

group = AppInfo.PACKAGE
version = AppInfo.VERSION

kotlin {
    explicitApi()
}

dependencies {
    implementation(Deps.Libs.Kotlinx.Coroutines)
    implementation(Deps.Libs.Kotlin.Script.Common)
    implementation(Deps.Libs.Kotlin.Script.CompilerEmbeddable)
    implementation(Deps.Libs.Kotlin.Script.Jvm)
    implementation(Deps.Libs.Kotlin.Script.JvmHost)
    implementation(Deps.Libs.Kotlin.Test.JUnit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
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
        artifactId = "scriptkt"
        name = "script.kt"
        description = "Convenient Kotlin Script wrapper"
    }
}