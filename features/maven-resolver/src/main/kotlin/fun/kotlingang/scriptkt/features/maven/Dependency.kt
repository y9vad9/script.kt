package `fun`.kotlingang.scriptkt.features.maven

@Target(AnnotationTarget.FILE)
@Repeatable
annotation class Dependency(val coordinates: String)