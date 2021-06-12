package `fun`.kotlingang.kscript

import `fun`.kotlingang.kscript.annotations.UnsafeConstructorArgs
import `fun`.kotlingang.kscript.configuration.setBaseClass
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.jvm.util.classpathFromClass
import kotlin.script.experimental.jvm.util.isError

open class Base(val test: String)

@Testable
class KScriptBaseClassTest {

    private val script = """
        println(test)
    """.trimIndent()

    @OptIn(UnsafeConstructorArgs::class)
    @Test
    fun test() = runBlocking {
        assertFalse(eval(script) {
            configuration.addClasspath(classpathFromClass<Base>()!!)
            configuration.setBaseClass<Base>("test")
        }.apply {
            reports.forEach(::println)
        }.isError())
    }
}