@file:Suppress("unused", "FunctionName")

package `fun`.kotlingang.kscript.configuration

import `fun`.kotlingang.kscript.annotations.DelicateKScriptAPI
import `fun`.kotlingang.kscript.annotations.ImportFile
import `fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs
import `fun`.kotlingang.kscript.configuration.impls.KScriptConfigurationImpl
import `fun`.kotlingang.kscript.internal.toMD5
import `fun`.kotlingang.kscript.toSourceCode
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.dependencies.DependsOn
import kotlin.script.experimental.dependencies.Repository
import kotlin.script.experimental.host.ScriptingHostConfiguration
import kotlin.script.experimental.jvm.compilationCache
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvmhost.CompiledScriptJarsCache

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
        val conf = this@toEvaluationConfiguration
        conf.baseClass?.arguments?.toList()?.let { constructorArgs(v = it) }
        implicitReceivers(*conf.implicitReceivers.map { it.instance }.toTypedArray())
    }

@DelicateKScriptAPI
public fun KScriptConfiguration.toCompilationConfiguration(dependencyHandler: (ScriptConfigurationRefinementContext) -> ResultWithDiagnostics<ScriptCompilationConfiguration>): ScriptCompilationConfiguration =
    ScriptCompilationConfiguration {
        val conf = this@toCompilationConfiguration
        conf.baseClass?.let(BaseClass::kClass)?.let { baseClass(it) }
        conf.defaultImports.forEach { defaultImports(it) }
        conf.includedScripts.forEach { importScripts(it.toSourceCode()) }

        refineConfiguration {
            onAnnotations(DependsOn::class, Repository::class, ImportFile::class, handler = { dependencyHandler(it) })
        }

        hostConfiguration(ScriptingHostConfiguration {
            jvm {
            if(buildCacheDirectory != null)
                compilationCache(
                    CompiledScriptJarsCache { script, _ ->
                            File(buildCacheDirectory, script.text.toMD5() + ".jar")
                        }
                )
            }
        })

        updateClasspath(conf.classpath)

    }

