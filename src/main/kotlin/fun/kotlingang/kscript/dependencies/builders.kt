package `fun`.kotlingang.kscript.dependencies

/**
 * Converts string representation of dependency to [Dependency].
 * @receiver [String] representation of dependency (e.x: fun.kotlingang.kds:core:1.0.0).
 * @return [Dependency].
 */
public fun String.toDependency(): Dependency {
    val parts = split(":", limit = 3)
    return Dependency(parts[0], parts[1], parts[2])
}