package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import java.io.File
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.dependencies.FileSystemDependenciesResolver
import kotlin.script.experimental.dependencies.addRepository

public class KScriptFilesResolver(private val paths: Array<File>) : KScriptResolver<MavenMetadata> {
    override suspend fun resolve(metadata: MavenMetadata): Collection<File> {
        val fileResolver = FileSystemDependenciesResolver(*paths)
        metadata.repositories.forEach { fileResolver.addRepository(it.toString()) }
        return metadata.dependencies.mapNotNull {
            fileResolver.resolve(it.toString()).valueOrNull()
        }.flatten()
    }
}