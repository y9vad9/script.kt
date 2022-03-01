![Maven metadata URL](https://img.shields.io/maven-metadata/v?label=%24version&metadataUrl=https%3A%2F%2Fmaven.kotlingang.fun%2Ffun%2Fkotlingang%2Fscriptkt%2Fscriptkt%2Fmaven-metadata.xml)

# script.kt

Convenient Kotlin Script wrapper. The main goal of this library is to simplify work with kotlin scripting.

## Examples

### Type-safe DSL

Simple DSL for scripts that do not need any customization, but just type-safe code.

```kotlin
moduleKt(mainScript = scriptOf(code = "..")) {
    setBaseClass<Foo>(arguments = listOf(Bar()))
    addImplicitReceiver<FooBar>(instance = FooBar())
}
```

### External features

If you need to customize somehow your compilation you can use features system:

```kotlin
moduleKt(mainScript) {
    compilation.install(MavenDependenciesResolver)
    evaluation.install(/* some evaluation feature */)
}
```

### Step-by-step configuration

If you need to divide your scripting processes you can use modular system:

```kotlin
val module = moduleOf(mainScript = scriptOf(code = "..."))
module.compilationConfiguration.apply { // for current state it is mutable
    baseClass = Foo::class
}
// now let's compile it
val compileResult = module.compile(compiler = JvmHostModuleCompiler())
if (compileResult.isError())
    throw RuntimeException("stub!")

val compiledModule = compileResult.valueOrThrow() // for this state compilation configuration is immutable
// let's add arguments of class `Foo`
compiledModule.evaluationConfiguration.apply { // it is mutable for current state
    baseClassArguments = listOf("...")
}

val evaluationResult = compiledModule.evaluate(evaluator = JvmHostModuleEvaluator())
if (evaluationResult.isError())
    throw RuntimeException("stub!")

// let's print our results
println((compileResult.reports + evaluationResult.reports).joinToString("\n") { it.render() })
```

## Implementation

For first, we need to add repository:

```kotlin
repositories {
    maven("https://maven.kotlingang.fun")
}
```

And then we need to add dependency:

```kotlin
dependencies {
    implementation("com.y9vad9.scriptkt:scriptkt:$version")
    // builtins (optional)
    implementation("com.y9vad9.scriptkt:maven-resolver:$version")
    implementation("com.y9vad9.scriptkt:import-script:$version")
}
```
