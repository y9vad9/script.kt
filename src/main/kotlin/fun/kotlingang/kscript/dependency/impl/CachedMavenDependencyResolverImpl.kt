package `fun`.kotlingang.kscript.dependency.impl

import `fun`.kotlingang.kscript.dependency.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

private const val DELIMITER_BETWEEN_DEPENDENCY_AND_FILES = ";"
private const val FILES_DELIMITER = "|"

internal class CachedDependencyResolverImpl(private val folder: File) : DependencyResolver {

    private fun getCachedDependencies(): List<ResolvedDependency> =
        File(folder, "cached_dependencies.txt")
            .readLines()
            .map {
                val parts = it.split(DELIMITER_BETWEEN_DEPENDENCY_AND_FILES)
                return@map parts.first()
                    .toDependency()
                    .toResolvedDependency(
                        parts[1].split(FILES_DELIMITER)
                            .map(::File)
                    )
            }

    override suspend fun resolve(dependency: Dependency): ResolvedDependency? = withContext(Dispatchers.IO) {
        return@withContext getCachedDependencies().firstOrNull { it.toString() == dependency.toString() }
    }
}