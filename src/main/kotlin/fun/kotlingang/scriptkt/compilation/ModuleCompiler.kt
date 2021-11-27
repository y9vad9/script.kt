package `fun`.kotlingang.scriptkt.compilation

import `fun`.kotlingang.scriptkt.PreevaluatedScriptsModule
import `fun`.kotlingang.scriptkt.ScriptsModule
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.jvm.util.isError

public interface ModuleCompiler {
    public suspend fun compile(module: ScriptsModule): ResultWithDiagnostics<PreevaluatedScriptsModule>
}

public suspend fun ModuleCompiler.compile(
    module: ScriptsModule,
    mapper: PreevaluatedScriptsModule.() -> Unit
): ResultWithDiagnostics<PreevaluatedScriptsModule> {
    val result = compile(module)
    if (!result.isError())
        result.valueOrThrow().apply(mapper)
    return result
}