package `fun`.kotlingang.kscript.impls

import `fun`.kotlingang.kscript.KScript
import `fun`.kotlingang.kscript.dependency.DependencyResolver
import `fun`.kotlingang.kscript.toCompilationConfiguration
import `fun`.kotlingang.kscript.toEvaluationConfiguration
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

internal class KScriptJvmImpl(private val code: String) : KScript {
    override val configuration: KScriptConfigurationImpl = KScriptConfigurationImpl()
    private var dependencyResolver: DependencyResolver? = null

    override fun setDependencyResolver(resolver: DependencyResolver) {
        dependencyResolver = resolver
    }

    override suspend fun eval(): ResultWithDiagnostics<EvaluationResult> {
        return BasicJvmScriptingHost().eval(
            code.toScriptSource(),
            configuration.toCompilationConfiguration(),
            configuration.toEvaluationConfiguration()
        )
    }
}