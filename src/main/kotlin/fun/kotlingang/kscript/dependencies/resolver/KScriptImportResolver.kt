package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.resolver.metadata.ExternalScriptsMetadata
import java.io.File

public class KScriptImportResolver(private val locations: List<File>) : KScriptResolver<ExternalScriptsMetadata> {
    override suspend fun resolve(metadata: ExternalScriptsMetadata): Collection<File> {
        return metadata.scripts.map { file ->
            locations.map { File(it, file) }.first(File::exists)
        }
    }
}