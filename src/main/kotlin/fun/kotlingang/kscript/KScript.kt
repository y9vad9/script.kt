package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics

public interface KScript : KScriptSource {
    /**
     * Configuration of the script.
     */
    public val configuration: KScriptConfiguration

    /**
     * Evaluates script.
     * @return [ResultWithDiagnostics] of [EvaluationResult].
     */
    public suspend fun eval(): ResultWithDiagnostics<EvaluationResult>
}