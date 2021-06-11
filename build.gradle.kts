plugins {
    kotlin(Plugins.JVM) version Versions.KOTLIN
}

group = Library.GROUP
version = Library.VERSION

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

applyPublication()

dependencies {
    implementation(reflect)
    implementation(scriptRuntime)
    implementation(compilerEmbeddable)
    implementation(scriptUtil)
    implementation(scriptingCompilerEmbeddable)
    implementation(coroutines)
    implementation(compilerCommon)
    implementation(compilerJvm)
    implementation(compilerJvmHost)
    implementation(ktScriptingDependencies)
    implementation(ktScriptingMavenDependencies)
    testImplementation(junitAPI)
    testRuntimeOnly(junitEngine)
}