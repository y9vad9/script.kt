package `fun`.kotlingang.scriptkt.features.maven

@Target(AnnotationTarget.FILE)
@Repeatable
annotation class Repository(val repository: String)