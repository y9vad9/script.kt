[![Last Version](https://badge.kotlingang.fun/maven/fun/kotlingang/kscript/kscript)](https://maven.kotlingang.fun/fun/kotlingang/kscript/)
[![Hits-of-Code](https://hitsofcode.com/github/kotlingang/kds)](https://hitsofcode.com/view/github/kotlingang/kds)

# KScript

Convenient kotlin script running engine for JVM.

## Examples

```kotlin
open class Test(val test: String)

val script = KScript(
    """
    println(test)
""".trimIndent()
).apply {
    configuration.addClasspath(classpathFromClassOrException(Test::class))
    configuration.setBaseClass<Test>(arguments = "test")
}.eval()
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