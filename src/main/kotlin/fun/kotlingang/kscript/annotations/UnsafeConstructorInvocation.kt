package `fun`.kotlingang.kscript.annotations

/**
 * Marks that constructor arguments should be put carefully.
 */
@RequiresOptIn(level = RequiresOptIn.Level.WARNING)
public annotation class UnsafeConstructorInvocation