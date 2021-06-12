[![Last Version](https://badge.kotlingang.fun/maven/fun/kotlingang/kscript/kscript)](https://maven.kotlingang.fun/fun/kotlingang/kscript/)
[![Hits-of-Code](https://hitsofcode.com/github/kotlingang/KScript)](https://hitsofcode.com/view/github/kotlingang/KScript)

# KScript

Convenient kotlin script running engine for JVM.

## Examples

### Base class:
```kotlin
open class Test(val test: String)

KScript("println(test)").apply {
    configuration.addClasspath(classpathFromClassOrException(Test::class))
    configuration.setBaseClass<Test>(arguments = arrayOf("test"))
}.eval()
```

### External dependencies
By default, library uses [MavenExternalResolver](src/main/kotlin/fun/kotlingang/kscript/configuration/impls/MavenExternalResolver.kt), but we can change it:
```kotlin
val script = """...""".toKScript()
script.configuration.externalResolver = FilesExternalResolver(File("..."), File("...")) // one of the default implementations.
script.eval()
```
Also, we can cache it:
```kotlin
script.configuration.externalResolver = CacheableMavenResolver(File("path_to_cache_folder"))
```
## Implementation
```kotlin
repositories {
    maven("https://maven.kotlingang.fun")
}
dependencies {
    implementation("fun.kotlingang.kscript:kscript:$version")
}
```
