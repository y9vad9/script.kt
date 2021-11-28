package `fun`.kotlingang.scriptkt.compilation

import kotlin.script.experimental.api.ScriptCompilationConfiguration

public interface CompilationFeature {
    /**
     * Evaluates before setting specified by user configuration.
     */
    public fun beforeConfigure(builder: ScriptCompilationConfiguration.Builder) {}

    /**
     * Evaluates after setting specified by user configuration.
     */
    public fun afterConfigure(builder: ScriptCompilationConfiguration.Builder) {}
}