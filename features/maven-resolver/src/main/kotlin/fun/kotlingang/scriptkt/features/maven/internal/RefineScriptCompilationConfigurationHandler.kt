package `fun`.kotlingang.scriptkt.features.maven.internal

import kotlin.script.experimental.api.RefineScriptCompilationConfigurationHandler
import kotlin.script.experimental.api.ResultWithDiagnostics
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext

internal inline fun refineScriptConfiguration(
    noinline body: (ScriptConfigurationRefinementContext) -> ResultWithDiagnostics<ScriptCompilationConfiguration>
): RefineScriptCompilationConfigurationHandler {
    return body
}