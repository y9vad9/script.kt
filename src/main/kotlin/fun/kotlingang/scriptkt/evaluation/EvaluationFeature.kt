package `fun`.kotlingang.scriptkt.evaluation

import kotlin.script.experimental.api.ScriptEvaluationConfiguration

public interface EvaluationFeature {
    public fun beforeConfigure(builder: ScriptEvaluationConfiguration.Builder.() -> Unit) {}
    public fun afterConfigure(builder: ScriptEvaluationConfiguration.Builder.() -> Unit) {}
}