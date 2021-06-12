package `fun`.kotlingang.kscript.annotations

/**
 * Tells that you should be careful with putting arguments of constructor.
 */
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class UnsafeConstructorArgs