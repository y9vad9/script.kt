package com.y9vad9.scriptkt.features.maven.resolver

import com.y9vad9.scriptkt.annotation.ExperimentalScriptKtApi
import com.y9vad9.scriptkt.compilation.CompilationFeature
import com.y9vad9.scriptkt.features.maven.Dependency
import com.y9vad9.scriptkt.features.maven.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.RepositoryCoordinates
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.classpathFromClass

class MavenResolverCompilationFeatureData(
    var resolver: MavenDependenciesResolver = MavenDependenciesResolver()
)

@ExperimentalScriptKtApi
class MavenResolverCompilationFeature(
    private val settings: MavenResolverCompilationFeatureData
) : CompilationFeature<MavenResolverCompilationFeatureData> {
    companion object Builder : CompilationFeature.Builder<MavenResolverCompilationFeatureData> {
        override fun install(block: MavenResolverCompilationFeatureData.() -> Unit): CompilationFeature<*> {
            return MavenResolverCompilationFeature(MavenResolverCompilationFeatureData().also(block))
        }
    }

    override fun afterConfigure(builder: ScriptCompilationConfiguration.Builder): Unit = with(builder) {
        defaultImports(Dependency::class, Repository::class)
        updateClasspath(classpathFromClass<Dependency>()!! + classpathFromClass<Repository>()!!)
        refineConfiguration {
            onAnnotations(Dependency::class, Repository::class) { context ->
                val reports: MutableList<ScriptDiagnostic> = mutableListOf()
                val classpath: MutableList<List<File>> = mutableListOf()
                val annotations =
                    context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
                        ?: return@onAnnotations context.compilationConfiguration.asSuccess()

                annotations.filter { it.annotation is Repository }.forEach {
                    settings.resolver.addRepository(RepositoryCoordinates((it.annotation as Repository).repository))
                }

                runBlocking {
                    annotations.filter { it.annotation is Dependency }.forEach { sourceAnnotation ->
                        launch {
                            settings.resolver.resolve((sourceAnnotation.annotation as Dependency).coordinates)
                                .also { result ->
                                    reports += result.reports
                                    if (result.valueOrNull() != null)
                                        classpath += result.valueOrThrow()
                                }
                        }
                    }
                }

                return@onAnnotations context.compilationConfiguration.with {
                    dependencies.append(classpath.map { JvmDependency(it) })
                }.asSuccess()
            }
        }
    }
}