import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import `fun`.kotlingang.scriptkt.compilation.JvmHostModuleCompiler
import `fun`.kotlingang.scriptkt.evaluation.JvmHostModuleEvaluator
import `fun`.kotlingang.scriptkt.features.maven.resolver.MavenResolverCompilationFeature
import `fun`.kotlingang.scriptkt.moduleKt
import `fun`.kotlingang.scriptkt.scriptOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import kotlin.script.experimental.api.valueOrThrow

@Testable
object MavenTest {
    private const val CODE = """
        @file:Dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-RC")
        
        import kotlinx.coroutines.*
        
        runBlocking {
            launch {
                println("test")
            }
        }
    """

    @OptIn(ExperimentalScriptKtApi::class)
    @Test
    fun testDependencies(): Unit = runBlocking {
        moduleKt(scriptOf(CODE)) {
            compilation.install(MavenResolverCompilationFeature)
        }.compile(JvmHostModuleCompiler()).apply {
            println(reports.joinToString("\n") { it.render() })
            valueOrThrow().evaluate(JvmHostModuleEvaluator())
        }
    }
}