package `fun`.kotlingang.scriptkt.evaluation

import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import `fun`.kotlingang.scriptkt.annotation.UnsafeArgumentsInput
import kotlin.script.experimental.api.ScriptEvaluationConfiguration
import kotlin.script.experimental.api.constructorArgs
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.api.providedProperties

public interface EvaluationConfiguration {
    public val baseClassArguments: List<Any?> get() = emptyList()

    @UnsafeArgumentsInput
    public val implicitReceivers: List<Any>
        get() = emptyList()

    @UnsafeArgumentsInput
    public val providedProperties: Map<String, Any?>

    public val features: List<EvaluationFeature<*>>
}


/**
 * This function can become `internal` due to its unnecessary for public usage.
 * If you need such function, please describe your use-case by creating issue on GitHub.
 */
@OptIn(UnsafeArgumentsInput::class)
@ExperimentalScriptKtApi
public fun EvaluationConfiguration.toScriptEvaluationConfiguration(): ScriptEvaluationConfiguration {
    return ScriptEvaluationConfiguration {
        implicitReceivers(v = this@toScriptEvaluationConfiguration.implicitReceivers)
        constructorArgs(v = this@toScriptEvaluationConfiguration.baseClassArguments)
        providedProperties(v = this@toScriptEvaluationConfiguration.providedProperties)
    }
}

public class MutableEvaluationConfiguration(
    baseClassArguments: List<Any?> = emptyList(),
    implicitReceivers: List<Any> = emptyList(),
    providedProperties: Map<String, Any?> = emptyMap()
) : EvaluationConfiguration {
    @UnsafeArgumentsInput
    override val implicitReceivers: MutableList<Any> = implicitReceivers.toMutableList()

    @UnsafeArgumentsInput
    override val providedProperties: MutableMap<String, Any?> = providedProperties.toMutableMap()
    override val features: MutableList<EvaluationFeature<*>> = mutableListOf()
    override val baseClassArguments: MutableList<Any?> = baseClassArguments.toMutableList()
}