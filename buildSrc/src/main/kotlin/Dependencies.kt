@file:Suppress("unused")

import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.kotlin

val DependencyHandlerScope.reflect get() = kotlin("reflect")
val DependencyHandlerScope.scriptRuntime get() = kotlin("script-runtime")
val DependencyHandlerScope.compilerEmbeddable get() = kotlin("compiler-embeddable")
val DependencyHandlerScope.scriptUtil get() = kotlin("script-util")
val DependencyHandlerScope.scriptingCompilerEmbeddable get() = kotlin("script-util")
val DependencyHandlerScope.coroutines get() = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"
val DependencyHandlerScope.compilerCommon get() = kotlin("scripting-common")
val DependencyHandlerScope.compilerJvm get() = kotlin("scripting-jvm")
val DependencyHandlerScope.compilerJvmHost get() = kotlin("scripting-jvm-host")
val DependencyHandlerScope.ktScriptingDependencies get() = kotlin("scripting-dependencies")
val DependencyHandlerScope.ktScriptingMavenDependencies get() = kotlin("scripting-dependencies-maven")
val DependencyHandlerScope.junitEngine get() = "org.junit.jupiter:junit-jupiter-engine:5.3.1"
val DependencyHandlerScope.junitAPI get() = "org.junit.jupiter:junit-jupiter-api:5.3.1"