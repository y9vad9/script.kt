package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import `fun`.kotlingang.kscript.dependencies.toDependency
import java.io.File

private const val DELIMITER_BETWEEN_DEPENDENCY_AND_FILES = ";"
private const val FILES_DELIMITER = "|"

public class KScriptCachedMavenResolver(private val cacheFolder: File) : KScriptResolver<MavenMetadata> {

    private fun getCachedDependencies(): List<Pair<Dependency, Collection<File>>> =
        File(cacheFolder, "cached_dependencies.txt")
            .readLines()
            .map {
                val parts = it.split(DELIMITER_BETWEEN_DEPENDENCY_AND_FILES)
                return@map Pair(
                    parts.first()
                        .toDependency(), parts[2].split(FILES_DELIMITER).map(::File)
                )
            }

    override suspend fun resolve(metadata: MavenMetadata): Collection<File> {
        val cached = getCachedDependencies()
        return metadata.dependencies.map { dependency ->
            cached.find { it.first.toString() == dependency.toString() }
        }.mapNotNull { it?.second }
            .flatten()
    }
}