package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.dependency.DependencyResolver
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics

public interface KScript {
    /**
     * Kotlin Script Configuration.
     */
    public val configuration: KScriptConfiguration

    /**
     * Sets dependency resolver.
     * @param resolver - external dependency resolver
     */
    public fun setDependencyResolver(resolver: DependencyResolver)

    /**
     * Evaluates script.
     * @return [ResultWithDiagnostics] of [EvaluationResult] with info about evaluation.
     */
    public suspend fun eval(): ResultWithDiagnostics<EvaluationResult>
}