package `fun`.kotlingang.kscript.configuration.impls

import `fun`.kotlingang.kscript.configuration.ExternalResolver
import `fun`.kotlingang.kscript.configuration.KScriptExternals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.dependencies.FileSystemDependenciesResolver

public class FilesExternalResolver(private val paths: Array<File>) : ExternalResolver {
    override suspend fun resolve(externals: KScriptExternals): Collection<File> = withContext(Dispatchers.IO) {
        val fileResolver = FileSystemDependenciesResolver(*paths)
        return@withContext externals.dependencies.mapNotNull {
            fileResolver.resolve(it.toString()).valueOrNull()
        }.flatten()
    }
}