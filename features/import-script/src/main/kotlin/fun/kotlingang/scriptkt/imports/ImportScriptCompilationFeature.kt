package `fun`.kotlingang.scriptkt.imports

import `fun`.kotlingang.scriptkt.compilation.CompilationFeature
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource

class ImportScriptCompilationFeature(private val rootDirectory: File) : CompilationFeature {
    override fun afterConfigure(builder: ScriptCompilationConfiguration.Builder) = with(builder) {
        refineConfiguration {
            onAnnotations(ImportScript::class) { context ->
                val sources = mutableListOf<SourceCode>()
                val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf {
                    it.isNotEmpty()
                } ?: return@onAnnotations context.compilationConfiguration.asSuccess()

                annotations.filter { it.annotation is ImportScript }.forEach { annotation ->
                    sources += (annotation.annotation as ImportScript).names.map { it.toScriptSource() }
                }

                return@onAnnotations context.compilationConfiguration.with {
                    importScripts.append(sources)
                }.asSuccess()
            }
        }
    }
}