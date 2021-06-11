package `fun`.kotlingang.kscript.dependency.impl

import `fun`.kotlingang.kscript.dependency.CachedDependencyResolver
import `fun`.kotlingang.kscript.dependency.Dependency
import `fun`.kotlingang.kscript.dependency.MavenDependencyResolver
import `fun`.kotlingang.kscript.dependency.ResolvedDependency
import java.io.File

internal class CacheableMavenDependencyResolverImpl(cacheFolder: File) : MavenDependencyResolver,
    MavenDependencyResolverImpl() {

    private val cachedDependencyResolver = CachedDependencyResolver(cacheFolder)

    override suspend fun resolve(dependency: Dependency): ResolvedDependency? {
        return cachedDependencyResolver.resolve(dependency) ?: super.resolve(dependency)
    }

}