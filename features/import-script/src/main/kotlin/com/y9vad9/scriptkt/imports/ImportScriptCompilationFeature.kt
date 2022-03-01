package com.y9vad9.scriptkt.imports

import com.y9vad9.scriptkt.annotation.ExperimentalScriptKtApi
import com.y9vad9.scriptkt.compilation.CompilationFeature
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource

class ImportScriptCompilationData {
    lateinit var rootDirectory: File
}

@ExperimentalScriptKtApi
class ImportScriptCompilationFeature(private val settings: ImportScriptCompilationData) :
    CompilationFeature<ImportScriptCompilationData> {
    companion object Builder : CompilationFeature.Builder<ImportScriptCompilationData> {
        override fun install(block: ImportScriptCompilationData.() -> Unit): CompilationFeature<*> {
            return ImportScriptCompilationFeature(
                ImportScriptCompilationData().apply(block)
            )
        }
    }

    override fun afterConfigure(builder: ScriptCompilationConfiguration.Builder) = with(builder) {
        refineConfiguration {
            onAnnotations(ImportScript::class) { context ->
                val sources = mutableListOf<SourceCode>()
                val annotations = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)?.takeIf {
                    it.isNotEmpty()
                } ?: return@onAnnotations context.compilationConfiguration.asSuccess()

                annotations.filter { it.annotation is ImportScript }.forEach { annotation ->
                    sources += (annotation.annotation as ImportScript).names.map {
                        File(
                            settings.rootDirectory,
                            it
                        ).readText().toScriptSource(it)
                    }
                }

                return@onAnnotations context.compilationConfiguration.with {
                    importScripts.append(sources)
                }.asSuccess()
            }
        }
    }
}