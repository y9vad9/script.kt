package com.y9vad9.scriptkt

import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import `fun`.kotlingang.scriptkt.annotation.UnsafeArgumentsInput
import `fun`.kotlingang.scriptkt.compilation.JvmHostModuleCompiler
import `fun`.kotlingang.scriptkt.evaluation.JvmHostModuleEvaluator
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.valueOrThrow
import kotlin.script.experimental.jvm.util.classpathFromClass
import kotlin.script.experimental.jvm.util.isError
import kotlin.test.assertFalse

open class BaseClass(private val argument: String) {
    fun baseClassMethod() = println(argument)
}

class ImplicitReceiver(private val argument: Int) {
    fun getIRMethod() = println(argument)
}

@Testable
object ModulesTest {
    @OptIn(UnsafeArgumentsInput::class)
    @Test
    fun testConfiguration(): Unit = runBlocking {
        scriptsModuleOf(
            scriptOf(
                """
                println(providedProperty)
                baseClassMethod()
                getIRMethod()
            """.trimIndent()
            )
        ).apply {
            compilationConfiguration.apply {
                classpath.addAll(listOf(classpathFromClass<BaseClass>()!! + classpathFromClass<ImplicitReceiver>()!!).flatten())
                baseClass = BaseClass::class
                implicitReceivers.add(ImplicitReceiver::class)
                providedProperties["providedProperty"] = Double::class
            }
        }.compile(JvmHostModuleCompiler()).apply {
            println(reports.joinToString("\n") { it.render(withStackTrace = true) })
            assertFalse(isError())
            assertFalse(reports.any { it.severity == ScriptDiagnostic.Severity.ERROR })
        }.valueOrThrow().apply {
            evaluationConfiguration.apply {
                baseClassArguments.add("test")
                implicitReceivers.add(ImplicitReceiver(1))
                providedProperties["providedProperty"] = 2.0
            }

            evaluate(JvmHostModuleEvaluator())
                .apply {
                    assertFalse(isError())
                    assertFalse(reports.any { it.severity == ScriptDiagnostic.Severity.ERROR })
                }
        }
    }

    @OptIn(ExperimentalScriptKtApi::class, UnsafeArgumentsInput::class)
    @Test
    fun dslTest(): Unit = runBlocking {
        moduleKt(scriptOf(code = "println(test)")) {
            provideProperty(name = "test", instance = "text")
            setBaseClass<BaseClass>(arguments = listOf("Test"))
            addImplicitReceiver(instance = ImplicitReceiver(2))
            addClasspath(classpathFromClass<BaseClass>()!!)
            addClasspath(classpathFromClass<ImplicitReceiver>()!!)
        }.compile(JvmHostModuleCompiler())
            .valueOrThrow()
            .evaluate(JvmHostModuleEvaluator())
            .valueOrThrow()
    }

}