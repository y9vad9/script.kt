package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.KScriptSource
import `fun`.kotlingang.kscript.configuration.BaseClass
import `fun`.kotlingang.kscript.configuration.ImplicitReceiver
import `fun`.kotlingang.kscript.configuration.KScriptConfiguration
import `fun`.kotlingang.kscript.configuration.KScriptResolvers
import java.io.File
import kotlin.reflect.KClass

internal class KScriptConfigurationImpl(override var externalResolvers: KScriptResolvers = KScriptResolvers()) : KScriptConfiguration {
    @OptIn(`fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs::class)
    override var baseClass: BaseClass? = null
    override val implicitReceivers: MutableSet<ImplicitReceiver<*>> = mutableSetOf()

    override fun <T : Any> addImplicitReceiver(kClass: KClass<T>, instance: T) {
        implicitReceivers.add(ImplicitReceiver(kClass, instance))
    }

    override val classpath: MutableSet<File> = mutableSetOf()
    override val defaultImports: MutableSet<String> = mutableSetOf()

    override fun addDefaultImport(import: String) {
        defaultImports.add(import)
    }

    override val includedScripts: MutableSet<KScriptSource> = mutableSetOf()
    override var buildCacheDirectory: File? = null

    override fun includeScript(script: KScriptSource) {
        includedScripts.add(script)
    }

    override fun addClasspath(files: Collection<File>) {
        classpath.addAll(files)
    }

}