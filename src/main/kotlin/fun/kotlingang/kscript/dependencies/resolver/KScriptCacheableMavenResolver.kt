package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import java.io.File

public class KScriptCacheableMavenResolver(folder: File) : KScriptResolver<MavenMetadata> {

    private val mavenResolver = KScriptMavenResolver()
    private val cachedExternalResolver = KScriptCachedMavenResolver(folder)

    override suspend fun resolve(metadata: MavenMetadata): Collection<File> {
        val resolved = List<List<File>?>(metadata.dependencies.size) { null }
        val resolvedCache = resolved
            .map { cachedExternalResolver.resolve(metadata) }
            .map { it.ifEmpty { null } }
        return resolvedCache.flatMap {
            it ?: mavenResolver.resolve(metadata)
        }
    }
}