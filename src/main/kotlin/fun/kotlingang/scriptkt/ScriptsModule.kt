package `fun`.kotlingang.scriptkt

import `fun`.kotlingang.scriptkt.annotation.UnsafeArgumentsInput
import `fun`.kotlingang.scriptkt.compilation.CompilationConfiguration
import `fun`.kotlingang.scriptkt.compilation.ModuleCompiler
import `fun`.kotlingang.scriptkt.compilation.MutableCompilationConfiguration
import `fun`.kotlingang.scriptkt.compilation.compile
import `fun`.kotlingang.scriptkt.evaluation.EvaluationConfiguration
import `fun`.kotlingang.scriptkt.evaluation.ModuleEvaluator
import `fun`.kotlingang.scriptkt.evaluation.MutableEvaluationConfiguration
import kotlin.script.experimental.api.CompiledScript
import kotlin.script.experimental.api.ResultValue
import kotlin.script.experimental.api.ResultWithDiagnostics

/**
 * The base interface of scripts module.
 */
public sealed interface ScriptsModule {
    public val mainScript: ScriptKt
    public val otherScripts: List<ScriptKt>

    public val compilationConfiguration: CompilationConfiguration
}

public class PrecompiledScriptsModule(override val mainScript: ScriptKt) : ScriptsModule {
    override val otherScripts: MutableList<ScriptKt> = mutableListOf()
    override val compilationConfiguration: MutableCompilationConfiguration = MutableCompilationConfiguration()

    public suspend fun compile(compiler: ModuleCompiler): ResultWithDiagnostics<PreevaluatedScriptsModule> =
        compiler.compile(this)
}

public sealed interface CompiledScriptsModule : ScriptsModule {
    public val evaluationConfiguration: EvaluationConfiguration
}

public class PreevaluatedScriptsModule(
    override val mainScript: ScriptKt,
    override val otherScripts: List<ScriptKt>,
    override val compilationConfiguration: CompilationConfiguration,
    private val compiledScript: CompiledScript
) : CompiledScriptsModule {
    override val evaluationConfiguration: MutableEvaluationConfiguration = MutableEvaluationConfiguration()

    public suspend fun evaluate(evaluator: ModuleEvaluator): ResultWithDiagnostics<EvaluatedScriptsModule> =
        evaluator.evaluate(compiledScript, this)
}

public class EvaluatedScriptsModule(
    override val mainScript: ScriptKt,
    override val otherScripts: List<ScriptKt>,
    override val compilationConfiguration: CompilationConfiguration,
    override val evaluationConfiguration: EvaluationConfiguration,
    public val resultValue: ResultValue
) : CompiledScriptsModule

public class NonCompiledConfiguredScriptsModule(
    override val mainScript: ScriptKt,
    override val compilationConfiguration: CompilationConfiguration,
    override val otherScripts: List<ScriptKt>,
    public val evaluationConfiguration: EvaluationConfiguration
) : ScriptsModule {
    @OptIn(UnsafeArgumentsInput::class)
    public suspend fun compile(compiler: ModuleCompiler): ResultWithDiagnostics<PreevaluatedScriptsModule> {
        return compiler.compile(this) {
            evaluationConfiguration.providedProperties.putAll(this@NonCompiledConfiguredScriptsModule.evaluationConfiguration.providedProperties)
            evaluationConfiguration.implicitReceivers.addAll(this@NonCompiledConfiguredScriptsModule.evaluationConfiguration.implicitReceivers)
            evaluationConfiguration.baseClassArguments.addAll(this@NonCompiledConfiguredScriptsModule.evaluationConfiguration.baseClassArguments)
            evaluationConfiguration.features.addAll(this@NonCompiledConfiguredScriptsModule.evaluationConfiguration.features)
        }
    }
}


public fun scriptsModuleOf(mainScript: ScriptKt, vararg other: ScriptKt): PrecompiledScriptsModule {
    val module = PrecompiledScriptsModule(mainScript)
    return module.apply {
        otherScripts += other
    }
}