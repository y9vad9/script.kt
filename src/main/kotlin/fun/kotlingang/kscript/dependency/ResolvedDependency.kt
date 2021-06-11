package `fun`.kotlingang.kscript.dependency

import java.io.File

public class ResolvedDependency(
    groupId: String,
    artifactId: String,
    version: String,
    public val files: Collection<File>
) : Dependency(
    groupId, artifactId,
    version
)