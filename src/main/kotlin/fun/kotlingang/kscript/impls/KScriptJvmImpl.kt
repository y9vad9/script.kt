package `fun`.kotlingang.kscript.impls

import `fun`.kotlingang.kscript.KScript
import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import `fun`.kotlingang.kscript.configuration.toCompilationConfiguration
import `fun`.kotlingang.kscript.configuration.toEvaluationConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.script.experimental.api.EvaluationResult
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost

internal class KScriptJvmImpl(
    private val code: String,
    override val configuration: KScriptConfiguration = KScriptConfiguration()
) : KScript {
    override val source: String get() = code

    @OptIn(`fun`.kotlingang.kscript.annotations.DelicateKScriptAPI::class)
    override suspend fun eval(): ResultWithDiagnostics<EvaluationResult> = withContext(Dispatchers.IO) {
        return@withContext BasicJvmScriptingHost().eval(
            code.toScriptSource(),
            configuration.toCompilationConfiguration(),
            configuration.toEvaluationConfiguration()
        )
    }
}