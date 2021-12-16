package `fun`.kotlingang.scriptkt.compilation

import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.updateClasspath

public interface CompilationConfiguration {
    public val baseClass: KClass<*> get() = Any::class
    public val implicitReceivers: List<KClass<*>> get() = emptyList()
    public val providedProperties: Map<String, KClass<*>>
    public val defaultImports: List<String>
    public val compilerOptions: List<String>
    public val classpath: Collection<File>

    @ExperimentalScriptKtApi
    public val features: List<CompilationFeature<*>>
}

/**
 * This function can become `internal` due to its probably unnecessary status for public usage.
 * If you need such function, please describe your use-case by creating issue on GitHub.
 */
@ExperimentalScriptKtApi
public fun CompilationConfiguration.toScriptCompilationConfiguration(): ScriptCompilationConfiguration =
    ScriptCompilationConfiguration {
        features.forEach { it.beforeConfigure(this) }
        baseClass(this@toScriptCompilationConfiguration.baseClass)
        implicitReceivers(v = this@toScriptCompilationConfiguration.implicitReceivers.map {
            KotlinType(it)
        })
        providedProperties(v = this@toScriptCompilationConfiguration.providedProperties.mapValues {
            KotlinType(it.value)
        })
        defaultImports(v = this@toScriptCompilationConfiguration.defaultImports)
        compilerOptions(v = this@toScriptCompilationConfiguration.compilerOptions)
        updateClasspath(classpath)
        features.forEach { it.afterConfigure(this) }
    }

public class MutableCompilationConfiguration(
    override var baseClass: KClass<*> = Any::class,
    implicitReceivers: List<KClass<*>> = emptyList()
) : CompilationConfiguration {
    override val implicitReceivers: MutableList<KClass<*>> = implicitReceivers.toMutableList()
    override val providedProperties: MutableMap<String, KClass<*>> = mutableMapOf()
    override val defaultImports: MutableList<String> = mutableListOf("`fun`.kotlingang.scriptkt.*")
    override val compilerOptions: MutableList<String> = mutableListOf()
    override val classpath: MutableCollection<File> = mutableSetOf()

    @ExperimentalScriptKtApi
    override val features: MutableList<CompilationFeature<*>> = mutableListOf()
}