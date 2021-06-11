package `fun`.kotlingang.kscript.dependency

public interface MavenDependencyResolver : DependencyResolver {
    /**
     * Adds [repository].
     * @param repository - repository to add.
     */
    public fun addRepository(repository: Repository)
}