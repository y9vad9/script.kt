package `fun`.kotlingang.kscript

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.jvm.util.isError

class KScriptImplicitTestClass(private val test: String) {
    val text get() = test
}

@Testable
class KScriptImplicitTest {

    private val script = "println(text)"

    private val kscript = KScript(script)

    @Test
    fun runScript() = runBlocking {
        kscript.configuration.addClasspath(classpathFromClassOrException(KScriptImplicitTestClass::class))
        kscript.configuration.addImplicitReceiver(KScriptImplicitTestClass("111"))
        assertFalse(kscript.eval().isError())
    }

}