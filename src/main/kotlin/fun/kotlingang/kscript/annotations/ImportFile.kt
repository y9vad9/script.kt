package `fun`.kotlingang.kscript.annotations

/**
 * Imports file to Script runtime.
 * @param path - path to file.
 */
@ScriptAnnotation
@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
public annotation class ImportFile(val path: String)