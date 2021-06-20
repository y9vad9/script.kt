package `fun`.kotlingang.kscript.impls

import `fun`.kotlingang.kscript.KScript
import `fun`.kotlingang.kscript.annotations.ImportFile
import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import `fun`.kotlingang.kscript.configuration.toCompilationConfiguration
import `fun`.kotlingang.kscript.configuration.toEvaluationConfiguration
import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.resolver.metadata.ExternalScriptsMetadata
import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import `fun`.kotlingang.kscript.dependencies.toDependency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.FileSystemDependenciesResolver
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.withUpdatedClasspath
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import `fun`.kotlingang.kscript.dependencies.Repository as Repo

internal class KScriptJvmImpl(
    private val code: String,
    override val configuration: KScriptConfiguration = KScriptConfiguration()
) : KScript {
    override val source: String get() = code

    @OptIn(`fun`.kotlingang.kscript.annotations.DelicateKScriptAPI::class)
    override suspend fun eval(): ResultWithDiagnostics<EvaluationResult> = withContext(Dispatchers.IO) {
        return@withContext BasicJvmScriptingHost().eval(
            code.toScriptSource(),
            configuration.toCompilationConfiguration { runBlocking { refineAnnotations(it) } },
            configuration.toEvaluationConfiguration()
        )
    }

    private suspend fun refineAnnotations(
        context: ScriptConfigurationRefinementContext
    ): ResultWithDiagnostics<ScriptCompilationConfiguration> {
        val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
            ?: return context.compilationConfiguration.asSuccess()
        val repositories: List<Repo> = annotations.filterIsInstance<Repo>()
        val dependencies: List<Dependency> = annotations.filterIsInstance<DependsOn>()
            .map(DependsOn::artifactsCoordinates)
            .flatMap { it.toList() }
            .map(String::toDependency)
        val externalScripts: List<String> = annotations.filterIsInstance<ImportFile>().map(ImportFile::path)
        val classpath: Collection<File> = configuration.externalResolvers.run {
            mavenResolver.resolve(
                MavenMetadata(repositories, dependencies)
            ) + (importFilesResolver?.resolve(
                ExternalScriptsMetadata(externalScripts)
            ) ?: emptyList())
        }
        return context.compilationConfiguration
            .withUpdatedClasspath(classpath)
            .asSuccess()
    }

}