package `fun`.kotlingang.kscript.dependencies.resolver

import `fun`.kotlingang.kscript.dependencies.resolver.metadata.ExternalResolverMetadata
import java.io.File

public interface KScriptResolver<M : ExternalResolverMetadata> {
    /**
     * Resolves [metadata].
     * @param metadata - metadata to resolve.
     */
    public suspend fun resolve(metadata: M): Collection<File>
}