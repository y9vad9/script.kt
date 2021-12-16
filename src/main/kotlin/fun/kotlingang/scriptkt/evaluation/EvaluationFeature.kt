package `fun`.kotlingang.scriptkt.evaluation

import kotlin.script.experimental.api.ScriptEvaluationConfiguration

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