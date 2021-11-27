package `fun`.kotlingang.scriptkt.evaluation

import `fun`.kotlingang.scriptkt.EvaluatedScriptsModule
import `fun`.kotlingang.scriptkt.PreevaluatedScriptsModule
import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import kotlin.script.experimental.api.CompiledScript
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.jvm.BasicJvmScriptEvaluator
import kotlin.script.experimental.jvm.util.isError

public class JvmHostModuleEvaluator(private val jvmHostEvaluator: BasicJvmScriptEvaluator = BasicJvmScriptEvaluator()) :
    ModuleEvaluator {
    @OptIn(ExperimentalScriptKtApi::class)
    override suspend fun evaluate(
        compiledScript: CompiledScript,
        module: PreevaluatedScriptsModule
    ): ResultWithDiagnostics<EvaluatedScriptsModule> {
        val result = jvmHostEvaluator.invoke(
            compiledScript, module.evaluationConfiguration.toScriptEvaluationConfiguration()
        )
        return if (result.isError())
            ResultWithDiagnostics.Failure(result.reports)
        else ResultWithDiagnostics.Success(
            EvaluatedScriptsModule(
                module.mainScript,
                module.otherScripts,
                module.compilationConfiguration,
                module.evaluationConfiguration,
                result.valueOrThrow().returnValue
            ), result.reports
        )
    }
}