package com.y9vad9.scriptkt

import com.y9vad9.scriptkt.annotation.ExperimentalScriptKtApi
import com.y9vad9.scriptkt.annotation.ScriptKtDSL
import com.y9vad9.scriptkt.annotation.UnsafeArgumentsInput
import com.y9vad9.scriptkt.compilation.CompilationFeature
import com.y9vad9.scriptkt.compilation.JvmHostModuleCompiler
import com.y9vad9.scriptkt.compilation.ModuleCompiler
import com.y9vad9.scriptkt.compilation.MutableCompilationConfiguration
import com.y9vad9.scriptkt.evaluation.EvaluationFeature
import com.y9vad9.scriptkt.evaluation.JvmHostModuleEvaluator
import com.y9vad9.scriptkt.evaluation.ModuleEvaluator
import com.y9vad9.scriptkt.evaluation.MutableEvaluationConfiguration
import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.jvm.util.isError

@ScriptKtDSL
public fun moduleKts(mainScript: ScriptKt, block: ScriptsModuleBuilder.() -> Unit): NonCompiledConfiguredScriptsModule {
    val builder = ScriptsModuleBuilderImpl(MutableCompilationConfiguration(), MutableEvaluationConfiguration())
    builder.apply(block)
    return NonCompiledConfiguredScriptsModule(
        mainScript, builder.compilationConfiguration, builder.otherScripts, builder.evaluationConfiguration
    )
}

@ScriptKtDSL
public fun moduleKts(mainScript: ScriptKt): NonCompiledConfiguredScriptsModule = moduleKts(mainScript) {}

@ScriptKtDSL
public interface ScriptsModuleBuilder {
    @UnsafeArgumentsInput
    public fun <T : Any> setBaseClass(kClass: KClass<T>, arguments: List<Any?>)
    public fun <T : Any> addImplicitReceiver(kClass: KClass<T>, instance: T)
    public fun <T : Any> provideProperty(name: String, kClass: KClass<T>, instance: T)
    public fun addClasspath(files: Collection<File>)
    public fun addScript(scriptKt: ScriptKt)
    public fun defaultImports(imports: List<String>)

    @ExperimentalScriptKtApi
    public val compilation: CompilationConfigurationBuilder

    @ExperimentalScriptKtApi
    public val evaluation: EvaluationConfigurationBuilder

    /**
     * Compilation configuration DSL. Used only for installing features due to safe dsl at [ScriptsModuleBuilder]
     */
    @ExperimentalScriptKtApi
    public interface CompilationConfigurationBuilder {
        /**
         * Compilation feature installing.
         * @param feature - feature to install.
         */
        public fun <TBuilder> install(feature: CompilationFeature.Builder<TBuilder>, block: TBuilder.() -> Unit)
    }

    @ExperimentalScriptKtApi
    public interface EvaluationConfigurationBuilder {
        /**
         * Compilation feature installing.
         * @param feature - feature to install.
         */
        public fun <TBuilder> install(feature: EvaluationFeature.Builder<TBuilder>, block: TBuilder.() -> Unit)
    }
}

@OptIn(ExperimentalScriptKtApi::class)
public fun ScriptsModuleBuilder.CompilationConfigurationBuilder.install(feature: CompilationFeature.Builder<*>) {
    install(feature) {}
}

@OptIn(ExperimentalScriptKtApi::class)
public fun ScriptsModuleBuilder.EvaluationConfigurationBuilder.install(feature: EvaluationFeature.Builder<*>) {
    install(feature) {}
}

public fun ScriptsModuleBuilder.defaultImports(vararg imports: String) {
    defaultImports(imports.toList())
}

public inline fun <reified T : Any> ScriptsModuleBuilder.provideProperty(name: String, instance: T) {
    provideProperty(name, T::class, instance)
}

@UnsafeArgumentsInput
public inline fun <reified T : Any> ScriptsModuleBuilder.setBaseClass(arguments: List<Any?>) {
    setBaseClass(T::class, arguments)
}

@UnsafeArgumentsInput
public inline fun <reified T : Any> ScriptsModuleBuilder.setBaseClass(vararg arguments: Any?) {
    setBaseClass(T::class, arguments.toList())
}

@OptIn(ExperimentalScriptKtApi::class)
@UnsafeArgumentsInput
public inline fun <reified T : Any> ScriptsModuleBuilder.addImplicitReceiver(instance: T) {
    addImplicitReceiver(T::class, instance)
}

/**
 * Compiles module and evaluates module.
 * If any error happened it will return nullable value.
 */
public suspend fun NonCompiledConfiguredScriptsModule.runScriptOrNull(
    compiler: ModuleCompiler = JvmHostModuleCompiler(),
    evaluator: ModuleEvaluator = JvmHostModuleEvaluator()
): EvaluatedScriptsModule? = try {
    runScriptOrThrow(compiler, evaluator)
} catch (e: Exception) {
    e.printStackTrace()
    null
}

/**
 * Compiles module and evaluates module.
 * If some error happened it will throw it.
 */
public suspend fun NonCompiledConfiguredScriptsModule.runScriptOrThrow(
    compiler: ModuleCompiler = JvmHostModuleCompiler(),
    evaluator: ModuleEvaluator = JvmHostModuleEvaluator()
): EvaluatedScriptsModule {
    return compile(compiler).valueOrThrow().evaluate(evaluator).valueOrThrow()
}

/**
 * Compiles module and evaluates module.
 * If some error happened it will throw it.
 */
public suspend fun NonCompiledConfiguredScriptsModule.runScriptReported(
    compiler: ModuleCompiler = JvmHostModuleCompiler(),
    evaluator: ModuleEvaluator = JvmHostModuleEvaluator()
): ResultWithDiagnostics<EvaluatedScriptsModule> {
    val reports: MutableList<ScriptDiagnostic> = mutableListOf()
    val compileResult = compile(compiler).also { reports.addAll(it.reports) }
    val evaluationResult = compileResult.valueOrNull()?.evaluate(evaluator)?.also { reports.addAll(it.reports) }
    return if (compileResult.isError() || compileResult.isError())
        ResultWithDiagnostics.Failure(reports)
    else ResultWithDiagnostics.Success(evaluationResult!!.valueOrThrow(), reports)
}

@OptIn(ExperimentalScriptKtApi::class)
internal class ScriptsModuleBuilderImpl(
    val compilationConfiguration: MutableCompilationConfiguration,
    val evaluationConfiguration: MutableEvaluationConfiguration
) : ScriptsModuleBuilder {

    val otherScripts: MutableList<ScriptKt> = mutableListOf()

    @UnsafeArgumentsInput
    override fun <T : Any> setBaseClass(kClass: KClass<T>, arguments: List<Any?>) {
        compilationConfiguration.baseClass = kClass
        evaluationConfiguration.baseClassArguments.clear()
        evaluationConfiguration.baseClassArguments.addAll(arguments)
    }

    @OptIn(UnsafeArgumentsInput::class)
    override fun <T : Any> addImplicitReceiver(kClass: KClass<T>, instance: T) {
        compilationConfiguration.implicitReceivers += kClass
        evaluationConfiguration.implicitReceivers += instance
    }

    @OptIn(UnsafeArgumentsInput::class)
    override fun <T : Any> provideProperty(name: String, kClass: KClass<T>, instance: T) {
        compilationConfiguration.providedProperties[name] = kClass
        evaluationConfiguration.providedProperties[name] = instance
    }

    override fun addClasspath(files: Collection<File>) {
        compilationConfiguration.classpath.addAll(files)
    }

    override fun addScript(scriptKt: ScriptKt) {
        otherScripts += scriptKt
    }

    override fun defaultImports(imports: List<String>) {
        compilationConfiguration.defaultImports.addAll(imports)
    }

    override val compilation: ScriptsModuleBuilder.CompilationConfigurationBuilder =
        object : ScriptsModuleBuilder.CompilationConfigurationBuilder {
            override fun <TBuilder> install(feature: CompilationFeature.Builder<TBuilder>, block: TBuilder.() -> Unit) {
                compilationConfiguration.features += feature.install(block)
            }
        }
    override val evaluation: ScriptsModuleBuilder.EvaluationConfigurationBuilder =
        object : ScriptsModuleBuilder.EvaluationConfigurationBuilder {
            override fun <TBuilder> install(feature: EvaluationFeature.Builder<TBuilder>, block: TBuilder.() -> Unit) {
                evaluationConfiguration.features += feature.install(block)
            }
        }
}