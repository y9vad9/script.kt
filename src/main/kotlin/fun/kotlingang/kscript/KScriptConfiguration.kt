package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.annotations.UnsafeConstructorInvocation
import java.io.File
import kotlin.reflect.KClass

public interface KScriptConfiguration {
    /**
     * Sets base class for script.
     * @param kClass - implicit receiver's class
     * @param value - instance of [kClass].
     */
    public fun <T : Any> addImplicitReceiver(kClass: KClass<T>, value: T)

    /**
     * Sets base class for script.
     * @param kClass - super class for script.
     */
    @UnsafeConstructorInvocation
    public fun setBaseClass(kClass: KClass<*>, vararg arguments: Any?)

    /**
     * Adds classpath to compilation configuration.
     * @param collection - classpath to add.
     */
    public fun addClasspath(collection: Collection<File>)
}

/**
 * Adds implicit receiver for script.
 * @param value - instance of [T].
 * @return [Unit].
 */
public inline fun <reified T : Any> KScriptConfiguration.addImplicitReceiver(value: T): Unit =
    addImplicitReceiver(T::class, value)

@UnsafeConstructorInvocation
public inline fun <reified T : Any> KScriptConfiguration.setBaseClass(vararg arguments: Any?): Unit =
    setBaseClass(T::class, *arguments)
