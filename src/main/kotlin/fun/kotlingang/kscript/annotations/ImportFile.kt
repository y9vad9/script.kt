package `fun`.kotlingang.kscript.annotations

/**
 * Imports file to Script runtime.
 * @param path - path to file.
 */
@ScriptAnnotation
@Retention(AnnotationRetention.SOURCE)
public annotation class ImportFile(val path: String)