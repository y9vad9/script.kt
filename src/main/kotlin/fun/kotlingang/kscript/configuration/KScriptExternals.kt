package `fun`.kotlingang.kscript.configuration

import `fun`.kotlingang.kscript.dependencies.Dependency
import `fun`.kotlingang.kscript.dependencies.Repository

public class KScriptExternals(
    /**
     * All defined script dependencies.
     */
    public val dependencies: List<Dependency>,
    /**
     * All defined repositories in script.
     */
    public val repositories: List<Repository>
)