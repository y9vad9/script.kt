@file:Suppress("FunctionName", "unused")

package `fun`.kotlingang.kscript.dependency

import `fun`.kotlingang.kscript.dependency.impl.CacheableMavenDependencyResolverImpl
import `fun`.kotlingang.kscript.dependency.impl.CachedDependencyResolverImpl
import `fun`.kotlingang.kscript.dependency.impl.MavenDependencyResolverImpl
import java.io.File

/**
 * Creates new instance of [MavenDependencyResolver].
 * @return [MavenDependencyResolver].
 */
public fun MavenDependencyResolver(): MavenDependencyResolver =
    MavenDependencyResolverImpl()

/**
 * Creates new instance of [DependencyResolver].
 * @param cacheFolder - folder with caches.
 * @return [DependencyResolver] with only cached dependencies resolves.
 */
public fun CachedDependencyResolver(cacheFolder: File): DependencyResolver =
    CachedDependencyResolverImpl(cacheFolder)

/**
 * Converts string representation of dependency to [Dependency].
 * @receiver [String] representation of dependency (e.x: fun.kotlingang.kds:core:1.0.0).
 * @return [Dependency].
 */
public fun String.toDependency(): Dependency {
    val parts = split(":")
    return Dependency(parts[0], parts[1], parts[2])
}

/**
 * Converts [Dependency] to [ResolvedDependency].
 * @param files - resolved dependency files.
 * @receiver [ResolvedDependency].
 */
public fun Dependency.toResolvedDependency(files: Collection<File>): ResolvedDependency =
    ResolvedDependency(groupId, artifactId, version, files)

/**
 * Creates instance of [DependencyResolver] as cacheable maven dependency resolver.
 * @param cacheFolder - folder for saving / reading cache.
 * @return [DependencyResolver] with cacheable maven dependency resolver.
 */
public fun CacheableMavenDependencyResolver(cacheFolder: File): DependencyResolver =
    CacheableMavenDependencyResolverImpl(cacheFolder)