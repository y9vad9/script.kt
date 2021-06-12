package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.configuration.ExternalResolver
import `fun`.kotlingang.kscript.configuration.KScriptExternals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.dependencies.addRepository
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver

/**
 * Default maven external resolver.
 */
public class MavenExternalResolver : ExternalResolver {
    override suspend fun resolve(externals: KScriptExternals): Collection<File> = withContext(Dispatchers.IO) {
        val mavenResolver = MavenDependenciesResolver().apply {
            externals.repositories.forEach { addRepository(it.url) }
        }
        return@withContext externals.dependencies.mapNotNull {
            mavenResolver.resolve(it.toString()).valueOrNull()
        }.flatten()
    }
}