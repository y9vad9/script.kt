package `fun`.kotlingang.kscript.dependency

public interface DependencyResolver {
    /**
     * Resolves [dependency].
     * @param dependency - dependency to resolve.
     * @return [List] of resolved [dependency].
     */
    public suspend fun resolve(dependency: Dependency): ResolvedDependency?
}