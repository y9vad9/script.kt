package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.configuration.ExternalResolver
import `fun`.kotlingang.kscript.configuration.KScriptExternals
import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.toDependency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

private const val DELIMITER_BETWEEN_DEPENDENCY_AND_FILES = ";"
private const val FILES_DELIMITER = "|"

public class CachedMavenExternalResolver(private val folder: File) : ExternalResolver {
    private fun getCachedDependencies(): List<Pair<Dependency, Collection<File>>> =
        File(folder, "cached_dependencies.txt")
            .readLines()
            .map {
                val parts = it.split(DELIMITER_BETWEEN_DEPENDENCY_AND_FILES)
                return@map Pair(
                    parts.first()
                        .toDependency(), parts[2].split(FILES_DELIMITER).map(::File)
                )
            }

    override suspend fun resolve(externals: KScriptExternals): Collection<File> = withContext(Dispatchers.IO) {
        val cached = getCachedDependencies()
        return@withContext externals.dependencies.map { dependency ->
            cached.find { it.first.toString() == dependency.toString() }
        }.mapNotNull { it?.second }
            .flatten()
    }
}