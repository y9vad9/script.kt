package `fun`.kotlingang.kscript.configuration

import `fun`.kotlingang.kscript.dependencies.resolver.KScriptMavenResolver
import `fun`.kotlingang.kscript.dependencies.resolver.KScriptResolver
import `fun`.kotlingang.kscript.dependencies.resolver.metadata.ExternalScriptsMetadata
import `fun`.kotlingang.kscript.dependencies.resolver.metadata.MavenMetadata
import java.io.File

public class KScriptResolvers {
    /**
     * External resolver for maven dependencies.
     */
    public var mavenResolver: KScriptResolver<MavenMetadata> =
        KScriptMavenResolver()

    /**
     * Resolver for [fun.kotlingang.kscript.annotations.ImportFile] annotations.
     */
    public var importFilesResolver: KScriptResolver<ExternalScriptsMetadata>? = null
}