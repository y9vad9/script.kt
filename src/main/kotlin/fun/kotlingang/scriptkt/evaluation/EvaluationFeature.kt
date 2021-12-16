package `fun`.kotlingang.scriptkt.evaluation

import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import kotlin.script.experimental.api.ScriptEvaluationConfiguration

@ExperimentalScriptKtApi
public interface EvaluationFeature<TConfiguration> {
    public interface Builder<TBuilder> {
        public fun install(block: TBuilder.() -> Unit): EvaluationFeature<*>
    }

    public fun beforeConfigure(
        configuration: TConfiguration,
        builder: ScriptEvaluationConfiguration.Builder.() -> Unit
    ) {
    }

    public fun afterConfigure(
        configuration: TConfiguration,
        builder: ScriptEvaluationConfiguration.Builder.() -> Unit
    ) {
    }
}