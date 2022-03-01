package com.y9vad9.scriptkt.evaluation

import com.y9vad9.scriptkt.EvaluatedScriptsModule
import com.y9vad9.scriptkt.PreevaluatedScriptsModule
import kotlin.script.experimental.api.CompiledScript
import kotlin.script.experimental.api.ResultWithDiagnostics

public interface ModuleEvaluator {
    public suspend fun evaluate(
        compiledScript: CompiledScript,
        module: PreevaluatedScriptsModule
    ): ResultWithDiagnostics<EvaluatedScriptsModule>
}