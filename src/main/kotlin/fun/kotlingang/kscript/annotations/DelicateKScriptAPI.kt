package `fun`.kotlingang.kscript.annotations

/**
 * Marks that method/class/constructor is delicate (test or internal purposed only).
 */
@RequiresOptIn(level = RequiresOptIn.Level.ERROR)
public annotation class DelicateKScriptAPI
