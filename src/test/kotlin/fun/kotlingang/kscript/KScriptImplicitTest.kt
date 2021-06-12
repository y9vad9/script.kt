package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.configuration.addImplicitReceiver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.jvm.util.classpathFromClass
import kotlin.script.experimental.jvm.util.isError

class Implicit(
    val test: String
)

@Testable
class KScriptImplicitTest {

    private val script = """
        println(test)
    """.trimIndent()

    @Test
    fun test(): Unit = runBlocking {
        assertFalse(eval(script) {
            configuration.addClasspath(classpathFromClass<Implicit>()!!)
            configuration.addImplicitReceiver(Implicit("test"))
        }.isError())
    }
}