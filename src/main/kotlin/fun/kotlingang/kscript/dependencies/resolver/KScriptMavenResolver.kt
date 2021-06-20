package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import java.io.File
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.dependencies.addRepository
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver

public class KScriptMavenResolver : KScriptResolver<MavenMetadata> {
    override suspend fun resolve(metadata: MavenMetadata): Collection<File> {
        val mavenResolver = MavenDependenciesResolver().apply {
            metadata.repositories.forEach { addRepository(it.url) }
        }
        return metadata.dependencies.mapNotNull {
            mavenResolver.resolve(it.toString()).valueOrNull()
        }.flatten()
    }
}