package `fun`.kotlingang.kscript.configuration

import `fun`.kotlingang.kscript.KScript
import `fun`.kotlingang.kscript.KScriptSource
import `fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs
import java.io.File
import kotlin.reflect.KClass

public interface KScriptConfiguration {

    /**
     * Base class for script.
     */
    public var baseClass: BaseClass?

    /**
     * List of implicit receivers.
     */
    public val implicitReceivers: Collection<ImplicitReceiver<*>>

    /**
     * Adds implicit receiver for script.
     * @param kClass - implicit receiver's class.
     * @param instance - instance of [kClass].
     */
    public fun <T : Any> addImplicitReceiver(kClass: KClass<T>, instance: T)

    /**
     * Handler for externals.
     */
    public var externalResolver: ExternalResolver

    /**
     * Classpath of current script.
     */
    public val classpath: Collection<File>

    /**
     * Default imports script imports.
     */
    public val defaultImports: Collection<String>

    /**
     * Adds default import to script.
     * @param import - package.
     */
    public fun addDefaultImport(import: String)

    /**
     * All included in build / running process scripts.
     */
    public val includedScripts: Collection<KScriptSource>

    /**
     * Includes script into compilation & evaluation process.
     * @param script - script to include.
     */
    public fun includeScript(script: KScriptSource)

    /**
     * Adds [files] to classpath.
     */
    public fun addClasspath(files: Collection<File>)
}

public class BaseClass(
    public val kClass: KClass<*>,
    @UnsafeConstructorArgs public val arguments: Array<out Any?>
)

public data class ImplicitReceiver<T : Any>(val kClass: KClass<*>, val instance: T)

/**
 * Adds implicit receiver.
 * @param instance - instance of [T].
 */
public inline fun <reified T : Any> KScriptConfiguration.addImplicitReceiver(instance: T): Unit =
    addImplicitReceiver(T::class, instance)

@UnsafeConstructorArgs
public inline fun <reified T : Any> KScriptConfiguration.setBaseClass(vararg args: Any?) {
    baseClass = BaseClass(T::class, args)
}

/**
 * Adds default imports to script.
 * @param packages - packages to import by default.
 */
public fun KScriptConfiguration.addDefaultImports(vararg packages: String): Unit = packages.forEach {
    addDefaultImport(it)
}

/**
 * Includes scripts to building / running process.
 * @param scripts - scripts to include.
 */
public fun KScriptConfiguration.includeScripts(vararg scripts: KScriptSource): Unit = scripts.forEach {
    includeScript(it)
}