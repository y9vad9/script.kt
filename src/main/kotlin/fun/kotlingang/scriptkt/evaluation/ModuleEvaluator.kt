package `fun`.kotlingang.scriptkt.evaluation

import `fun`.kotlingang.scriptkt.EvaluatedScriptsModule
import `fun`.kotlingang.scriptkt.PreevaluatedScriptsModule
import kotlin.script.experimental.api.CompiledScript
import kotlin.script.experimental.api.ResultWithDiagnostics

public interface ModuleEvaluator {
    public suspend fun evaluate(
        compiledScript: CompiledScript,
        module: PreevaluatedScriptsModule
    ): ResultWithDiagnostics<EvaluatedScriptsModule>
}