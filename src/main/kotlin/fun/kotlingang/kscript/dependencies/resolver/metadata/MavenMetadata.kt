package `fun`.kotlingang.kscript.dependencies.resolver.metadata

import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.Repository

public class MavenMetadata(
    public val repositories: List<Repository>,
    public val dependencies: List<Dependency>
) : ExternalResolverMetadata