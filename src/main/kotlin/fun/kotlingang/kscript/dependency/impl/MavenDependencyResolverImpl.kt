package `fun`.kotlingang.kscript.dependency.impl

import `fun`.kotlingang.kscript.dependency.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.script.experimental.api.valueOrNull
import kotlin.script.experimental.dependencies.maven.MavenDependenciesResolver

internal open class MavenDependencyResolverImpl : MavenDependencyResolver {

    private val repositories: MutableSet<Repository> = mutableSetOf()

    override suspend fun resolve(dependency: Dependency): ResolvedDependency? = withContext(Dispatchers.IO) {
        val mavenResolver = MavenDependenciesResolver().apply {
            repositories.forEach { addRepository(it) }
        }
        return@withContext mavenResolver.resolve(dependency.toString())
            .valueOrNull()
            ?.let(dependency::toResolvedDependency)
    }

    override fun addRepository(repository: Repository) {
        repositories.add(repository)
    }
}