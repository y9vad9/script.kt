package `fun`.kotlingang.scriptkt.annotation

/**
 * Marks that element that annotated shouldn't be used by user directly, but only from user-defined feature
 * to save code consistency.
 */
@RequiresOptIn(
    message = "There shouldn't be any direct access to annotated elements outside of defined feature",
    level = RequiresOptIn.Level.ERROR
)
public annotation class FeatureAccess