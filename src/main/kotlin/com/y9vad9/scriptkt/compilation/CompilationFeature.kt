package com.y9vad9.scriptkt.compilation

import com.y9vad9.scriptkt.annotation.ExperimentalScriptKtApi
import kotlin.script.experimental.api.ScriptCompilationConfiguration

@ExperimentalScriptKtApi
public interface CompilationFeature<TConfiguration> {

    public interface Builder<TBuilder> {
        public fun install(block: TBuilder.() -> Unit): CompilationFeature<*>
    }

    /**
     * Evaluates before setting specified by user configuration.
     */
    public fun beforeConfigure(builder: ScriptCompilationConfiguration.Builder) {}

    /**
     * Evaluates after setting specified by user configuration.
     */
    public fun afterConfigure(builder: ScriptCompilationConfiguration.Builder) {}
}