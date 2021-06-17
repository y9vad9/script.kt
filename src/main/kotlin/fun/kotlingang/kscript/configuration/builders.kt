@file:Suppress("unused", "FunctionName")

package `fun`.kotlingang.kscript.configuration

import `fun`.kotlingang.kscript.annotations.DelicateKScriptAPI
import `fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs
import `fun`.kotlingang.kscript.configuration.impls.KScriptConfigurationImpl
import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.toDependency
import `fun`.kotlingang.kscript.internal.Digest.toMD5
import `fun`.kotlingang.kscript.toSourceCode
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.host.ScriptingHostConfiguration
import kotlin.script.experimental.jvm.compilationCache
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.withUpdatedClasspath
import kotlin.script.experimental.jvmhost.CompiledScriptJarsCache
import `fun`.kotlingang.kscript.dependencies.Repository as Repo

/**
 * Creates instance of [BaseClass].
 * @param arguments - arguments of super class.
 * @return [BaseClass].
 */
@UnsafeConstructorArgs
public inline fun <reified T : Any> BaseClass(vararg arguments: Any?): BaseClass =
    BaseClass(T::class, arguments)

/**
 * Creates instance of [ImplicitReceiver].
 * @param instance - instance of [T].
 * @return [ImplicitReceiver].
 */
public inline fun <reified T : Any> ImplicitReceiver(instance: T): ImplicitReceiver<T> =
    ImplicitReceiver(T::class, instance)

public fun KScriptConfiguration(): KScriptConfiguration = KScriptConfigurationImpl()

@DelicateKScriptAPI
public fun KScriptConfiguration.toEvaluationConfiguration(): ScriptEvaluationConfiguration =
    ScriptEvaluationConfiguration {
        val configuration = this@toEvaluationConfiguration
        configuration.baseClass?.arguments?.toList()?.let {
            constructorArgs(v = it)
        }
        implicitReceivers(*configuration.implicitReceivers.map { it.instance }.toTypedArray())
    }

@DelicateKScriptAPI
public fun KScriptConfiguration.toCompilationConfiguration(): ScriptCompilationConfiguration =
    ScriptCompilationConfiguration {
        val configuration = this@toCompilationConfiguration
        configuration.baseClass?.let(BaseClass::kClass)?.let { baseClass(it) }
        implicitReceivers(*configuration.implicitReceivers.map { KotlinType(it.kClass) }.toTypedArray())
        configuration.defaultImports.forEach { defaultImports(it) }
        configuration.includedScripts.forEach { importScripts(it.toSourceCode()) }
        refineConfiguration {
            onAnnotations(
                DependsOn::class,
                Repository::class,
                handler = configuration.externalResolver::resolveDependencyAnnotations
            )
        }
        hostConfiguration(ScriptingHostConfiguration {
            jvm {
                if(configuration.buildCacheDirectory != null)
                    compilationCache(
                        CompiledScriptJarsCache { script, _ ->
                            File(configuration.buildCacheDirectory, script.text.toMD5() + ".jar")
                        }
                    )
            }
        })
        updateClasspath(configuration.classpath)
    }

private fun ExternalResolver.resolveDependencyAnnotations(
    context: ScriptConfigurationRefinementContext
): ResultWithDiagnostics<ScriptCompilationConfiguration> = runBlocking {
    val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf { it.isNotEmpty() }
        ?: return@runBlocking context.compilationConfiguration.asSuccess()
    val repositories = mutableListOf<Repo>()
    val dependencies = mutableListOf<Dependency>()
    for (annotation in annotations) {
        if (annotation.annotation is Repository)
            (annotation.annotation as Repository).repositoriesCoordinates.forEach {
                repositories.add(Repo(it))
            }
        else if (annotation.annotation is DependsOn)
            (annotation.annotation as DependsOn).artifactsCoordinates.forEach {
                dependencies.add(it.toDependency())
            }
    }
    return@runBlocking context.compilationConfiguration.withUpdatedClasspath(
        resolve(
            KScriptExternals(
                dependencies,
                repositories
            )
        )
    ).asSuccess()
}
