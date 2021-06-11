[![Last Version](https://badge.kotlingang.fun/maven/fun/kotlingang/kscript/kscript)](https://maven.kotlingang.fun/fun/kotlingang/kscript/)
[![Hits-of-Code](https://hitsofcode.com/github/kotlingang/KScript)](https://hitsofcode.com/view/github/kotlingang/KScript)

# KScript

Convenient kotlin script running engine for JVM.

## Examples

```kotlin
open class Test(val test: String)

KScript("println(test)").apply {
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
