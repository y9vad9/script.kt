package `fun`.kotlingang.scriptkt.imports

@Repeatable
@Target(AnnotationTarget.FILE)
annotation class ImportScript(vararg val names: String)
