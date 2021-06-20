package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.annotations.DelicateKScriptAPI
import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import `fun`.kotlingang.kscript.impls.KScriptJvmImpl
import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.SourceCode
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.util.classpathFromClass

/**
 * Creates instance of [KScript].
 * @param code - script source code.
 * @return [KScript].
 */
public fun KScript(code: String): KScript = KScriptJvmImpl(code)

/**
 * Creates instance of [KScript].
 * @param file - file with script.
 * @return [KScript].
 */
public fun KScript(file: File): KScript = KScript(file.readText())

/**
 * Evaluates [code].
 * @param code - script source code.
 * @return [ResultWithDiagnostics] of [EvaluationResult].
 */
public suspend fun eval(code: String, consumer: KScript.() -> Unit): ResultWithDiagnostics<EvaluationResult> =
    KScript(code).apply(consumer).eval()

/**
 * Evaluates code from [file].
 */
public suspend fun eval(file: File, consumer: KScript.() -> Unit): ResultWithDiagnostics<EvaluationResult> =
    eval(file.readText(), consumer)

internal fun KScriptSource.toSourceCode(): SourceCode = source.toScriptSource()

/**
 * Creates instance of [KScript].
 * @param code - code of script.
 * @param configuration - script configuration.
 */
public fun KScript(code: String, configuration: KScriptConfiguration): KScript = KScript(code, configuration)

/**
 * Creates [KScript] from [String]
 */
public fun String.toKScript(configuration: KScriptConfiguration = KScriptConfiguration()): KScript =
    KScript(this, configuration)

/**
 * Gets classpath from [kClass] or throws exception.
 */
@Throws(IllegalStateException::class)
public fun classpathFromClassOrException(kClass: KClass<*>): Collection<File> =
    classpathFromClass(kClass) ?: error("unable to get classpath.")

/**
 * Gets classpath from [C].
 */
public inline fun <reified C> classpathFromClassOrException(): Collection<File> =
    classpathFromClassOrException(C::class)

/**
 * Gets class from [kClass] or returns null.
 * @return [Collection] of [File] or null.
 */
public fun classpathFromClassOrNull(kClass: KClass<*>): Collection<File>? =
    classpathFromClass(kClass)

public inline fun <reified C> classpathFromClassOrNull(): Collection<File>? =
    classpathFromClassOrNull(C::class)

