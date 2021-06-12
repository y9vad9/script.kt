package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.configuration.BaseClass
import `fun`.kotlingang.kscript.configuration.ExternalResolver
import `fun`.kotlingang.kscript.configuration.ImplicitReceiver
import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import java.io.File
import kotlin.reflect.KClass

internal class KScriptConfigurationImpl : KScriptConfiguration {
    @OptIn(`fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs::class)
    override var baseClass: BaseClass? = null
    override val implicitReceivers: MutableSet<ImplicitReceiver<*>> = mutableSetOf()

    override fun <T : Any> addImplicitReceiver(kClass: KClass<T>, instance: T) {
        implicitReceivers.add(ImplicitReceiver(kClass, instance))
    }

    override var externalResolver: ExternalResolver = MavenExternalResolver()
    override val classpath: MutableSet<File> = mutableSetOf()

    override fun addClasspath(files: Collection<File>) {
        classpath.addAll(files)
    }

}