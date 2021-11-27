package `fun`.kotlingang.scriptkt.compilation

import kotlin.script.experimental.api.ScriptCompilationConfiguration

public interface CompilationFeature {
    /**
     * Evaluates before setting specified by user configuration.
     */
    public fun beforeConfiguring(builder: ScriptCompilationConfiguration.Builder) {}

    /**
     * Evaluates after setting specified by user configuration.
     */
    public fun afterConfiguring(builder: ScriptCompilationConfiguration.Builder) {}
}