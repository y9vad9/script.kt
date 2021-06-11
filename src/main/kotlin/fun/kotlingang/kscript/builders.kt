package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.annotations.DelicateKScriptAPI
import `fun`.kotlingang.kscript.impls.BaseClass
import `fun`.kotlingang.kscript.impls.KScriptConfigurationImpl
import `fun`.kotlingang.kscript.impls.KScriptJvmImpl
import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.updateClasspath
import kotlin.script.experimental.jvm.util.classpathFromClass

/**
 * @return [KScript] from [code].
 */
public fun KScript(code: String): KScript = KScriptJvmImpl(code)

/**
 * @return [KScript] from [file].
 */
public fun KScript(file: File): KScript = KScriptJvmImpl(file.readText())

/**
 * @return new instance of [KScriptConfiguration].
 */
@DelicateKScriptAPI
public fun KScriptConfiguration(): KScriptConfiguration = KScriptConfigurationImpl()

internal fun KScriptConfigurationImpl.toCompilationConfiguration(): ScriptCompilationConfiguration =
    ScriptCompilationConfiguration {
        val configuration = this@toCompilationConfiguration
        configuration.baseClass?.let(BaseClass::kClass)?.let { baseClass(it) }
        implicitReceivers(*configuration.implicitReceivers.map { KotlinType(it.kClass) }.toTypedArray())
        updateClasspath(configuration.classPath)
    }

/**
 * @return [List] of [File] from [kClass] or throws [IllegalStateException].
 */
public fun classpathFromClassOrException(kClass: KClass<*>): List<File> =
    classpathFromClass(kClass) ?: error("Unable to get classpath from class ${kClass.simpleName}")

internal fun KScriptConfigurationImpl.toEvaluationConfiguration(): ScriptEvaluationConfiguration =
    ScriptEvaluationConfiguration {
        val configuration = this@toEvaluationConfiguration
        configuration.baseClass?.arguments?.toList()?.let {
            constructorArgs(v = it)
        }
        implicitReceivers(*configuration.implicitReceivers.map { it.value }.toTypedArray())
    }