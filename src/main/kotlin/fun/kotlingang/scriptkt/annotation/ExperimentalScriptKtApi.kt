package `fun`.kotlingang.scriptkt.annotation

/**
 * Marks that API is experimental. It can be changed at any time without any alternative or notification.
 * If you use such API, please describe your use-case by creating corresponding issue on GitHub.
 */
@RequiresOptIn(
    message = "Experimental ScriptKt API. It can be changed at any time without any alternative or notification.",
    level = RequiresOptIn.Level.ERROR
)
public annotation class ExperimentalScriptKtApi