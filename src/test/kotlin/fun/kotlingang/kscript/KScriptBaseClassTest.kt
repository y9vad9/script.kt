package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.annotations.UnsafeConstructorInvocation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.jvm.util.isError

open class KScriptBaseClassTestClass(private val test: String) {
    val text get() = test
}

@Testable
class KScriptBaseClassTest {

    private val script = "println(text)"

    private val kscript = KScript(script)

    @OptIn(UnsafeConstructorInvocation::class)
    @Test
    fun run() = runBlocking {
        kscript.configuration.addClasspath(classpathFromClassOrException(KScriptBaseClassTestClass::class))
        kscript.configuration.setBaseClass<KScriptBaseClassTestClass>("test")
        val result = kscript.eval()
        assert(!result.isError())
    }

}