package `fun`.kotlingang.kscript.annotations

/**
 * Marks that annotations used into scripts.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS)
public annotation class ScriptAnnotation
