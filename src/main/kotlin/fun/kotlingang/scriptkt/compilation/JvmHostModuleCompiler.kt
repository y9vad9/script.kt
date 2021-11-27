@file:OptIn(ExperimentalScriptKtApi::class)

package `fun`.kotlingang.scriptkt.compilation

import `fun`.kotlingang.scriptkt.PreevaluatedScriptsModule
import `fun`.kotlingang.scriptkt.ScriptsModule
import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.util.isError
import kotlin.script.experimental.jvmhost.JvmScriptCompiler

public class JvmHostModuleCompiler(private val jvmCompiler: JvmScriptCompiler = JvmScriptCompiler()) : ModuleCompiler {
    override suspend fun compile(module: ScriptsModule): ResultWithDiagnostics<PreevaluatedScriptsModule> {
        val result = jvmCompiler.invoke(
            module.mainScript.code.toScriptSource(name = module.mainScript.name),
            module.compilationConfiguration.toScriptCompilationConfiguration()
        )
        return if (result.isError())
            ResultWithDiagnostics.Failure(result.reports)
        else ResultWithDiagnostics.Success(
            PreevaluatedScriptsModule(
                module.mainScript, module.otherScripts, module.compilationConfiguration, result.valueOrThrow()
            ), result.reports
        )
    }
}