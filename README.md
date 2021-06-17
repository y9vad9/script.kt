[![Last Version](https://badge.kotlingang.fun/maven/fun/kotlingang/kscript/kscript)](https://maven.kotlingang.fun/fun/kotlingang/kscript/)
[![Hits-of-Code](https://hitsofcode.com/github/kotlingang/KScript)](https://hitsofcode.com/view/github/kotlingang/KScript)

# KScript
Convenient kotlin script running engine for JVM.

## How it works

### Base class
```kotlin
open class Test(val test: String) // this class will be super class of script.

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
### Build caching
```kotlin
val script = "...".toKScript()
script.configuration.buildCacheDirectory = File(path)
script.eval()
```
Caching depends on the code (based on it, md5 is generated with a cache build) 
and in order to update the cache, you will need to delete the cache file / folder or update something in the script.

## Implementation
> For now not everything is implemented, but it will be available [upon request](https://github.com/kotlingang/KScript/issues/new).
```kotlin
repositories {
    maven("https://maven.kotlingang.fun")
}
dependencies {
    implementation("fun.kotlingang.kscript:kscript:$version")
}
```
