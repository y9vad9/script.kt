package `fun`.kotlingang.kscript.dependencies

public data class Dependency(
    public val groupId: String,
    public val artifactId: String,
    public val version: String
) {
    override fun toString(): String = "$groupId:$artifactId:$version"
}