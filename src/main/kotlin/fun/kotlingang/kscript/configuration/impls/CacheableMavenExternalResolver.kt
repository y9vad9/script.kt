package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.configuration.ExternalResolver
import `fun`.kotlingang.kscript.configuration.KScriptExternals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

public class CacheableMavenExternalResolver(folder: File) : ExternalResolver {

    private val mavenResolver = MavenExternalResolver()
    private val cachedExternalResolver = CachedMavenExternalResolver(folder)

    override suspend fun resolve(externals: KScriptExternals): Collection<File> = withContext(Dispatchers.IO) {
        val resolved = List<List<File>?>(externals.dependencies.size) { null }
        val resolvedCache = resolved
            .map { cachedExternalResolver.resolve(externals) }
            .map { it.ifEmpty { null } }
        val resolvedByMaven = resolvedCache.map {
            it ?: mavenResolver.resolve(externals)
        }
        return@withContext resolvedByMaven.flatten()
    }

}