package `fun`.kotlingang.kscript.configuration

import java.io.File

public interface ExternalResolver {
    /**
     * Resolves [externals] and returns classpath.
     * @return [Collection] of [File] with classpath.
     */
    public suspend fun resolve(externals: KScriptExternals): Collection<File>
}