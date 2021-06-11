package `fun`.kotlingang.kscript.dependency

/**
 * Dependency info.
 */
public open class Dependency(
    public val groupId: String,
    public val artifactId: String,
    public val version: String
) {
    override fun toString(): String = "$groupId:$artifactId:$version"
}
