package `fun`.kotlingang.scriptkt.features.maven.resolver

import `fun`.kotlingang.scriptkt.compilation.CompilationFeature
import `fun`.kotlingang.scriptkt.features.maven.Dependency
import `fun`.kotlingang.scriptkt.features.maven.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.RepositoryCoordinates
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver
import kotlin.script.experimental.jvm.JvmDependency
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.classpathFromClass

class MavenResolverCompilationFeature(
    private val resolver: MavenDependenciesResolver = MavenDependenciesResolver()
) : CompilationFeature {

    companion object Default : CompilationFeature by MavenResolverCompilationFeature()

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
                    resolver.addRepository(RepositoryCoordinates((it.annotation as Repository).repository))
                }

                runBlocking {
                    annotations.filter { it.annotation is Dependency }.forEach { sourceAnnotation ->
                        launch {
                            resolver.resolve((sourceAnnotation.annotation as Dependency).coordinates).also { result ->
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