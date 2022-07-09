import com.y9vad9.scriptkt.annotation.ExperimentalScriptKtApi
import com.y9vad9.scriptkt.compilation.JvmHostModuleCompiler
import com.y9vad9.scriptkt.evaluation.JvmHostModuleEvaluator
import com.y9vad9.scriptkt.features.maven.resolver.MavenResolverCompilationFeature
import com.y9vad9.scriptkt.install
import com.y9vad9.scriptkt.moduleKts
import com.y9vad9.scriptkt.scriptOf
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
        moduleKts(scriptOf(CODE)) {
            compilation.install(MavenResolverCompilationFeature)
        }.compile(JvmHostModuleCompiler()).apply {
            println(reports.joinToString("\n") { it.render() })
            valueOrThrow().evaluate(JvmHostModuleEvaluator())
        }
    }
}