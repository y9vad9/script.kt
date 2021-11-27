package `fun`.kotlingang.scriptkt

public fun scriptOf(name: String? = null, code: String): ScriptKt = ScriptKt(name, code)
public fun scriptOf(code: String): ScriptKt = scriptOf(null, code)

public class ScriptKt(public val name: String? = null, public val code: String)

public fun ScriptKt.toModule(): PrecompiledScriptsModule = PrecompiledScriptsModule(this)