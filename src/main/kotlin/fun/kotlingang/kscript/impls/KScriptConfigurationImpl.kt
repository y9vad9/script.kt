package `fun`.kotlingang.kscript.impls

import `fun`.kotlingang.kscript.KScriptConfiguration
import `fun`.kotlingang.kscript.annotations.UnsafeConstructorInvocation
import java.io.File
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
internal class KScriptConfigurationImpl : KScriptConfiguration {

    internal val implicitReceivers: MutableList<ImplicitReceiver<*>> = mutableListOf()
    internal var baseClass: BaseClass? = null
    internal val classPath: MutableList<File> = mutableListOf()

    override fun <T : Any> addImplicitReceiver(kClass: KClass<T>, value: T) {
        implicitReceivers.add(ImplicitReceiver(kClass, value))
    }

    @UnsafeConstructorInvocation
    override fun setBaseClass(kClass: KClass<*>, vararg arguments: Any?) {
        baseClass = BaseClass(kClass, arguments)
    }

    override fun addClasspath(collection: Collection<File>) {
        classPath.addAll(collection)
    }
}

internal class ImplicitReceiver<T : Any>(
    val kClass: KClass<*>,
    val value: T
)

internal class BaseClass(
    val kClass: KClass<*>,
    val arguments: Array<out Any?>
)