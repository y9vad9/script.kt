package `fun`.kotlingang.scriptkt

import `fun`.kotlingang.scriptkt.annotation.ExperimentalScriptKtApi
import java.io.File
import kotlin.reflect.KClass
import kotlin.script.experimental.jvm.util.classpathFromClass

public fun <T : Any> classpathFromClassOrNull(kClass: KClass<T>): Collection<File>? {
    return classpathFromClass(kClass)
}

public fun <T : Any> classpathFromClassOrThrow(kClass: KClass<T>): Collection<File> {
    return classpathFromClassOrNull(kClass) ?: throw NullPointerException(
        "An error occurred while getting classpath from ${kClass.simpleName}. Throwing on null."
    )
}

public inline fun <reified T : Any> classpathFromClassOrNull(): Collection<File>? = classpathFromClassOrNull(T::class)
public inline fun <reified T : Any> classpathFromClassOrThrow(): Collection<File> = classpathFromClassOrThrow(T::class)


@ExperimentalScriptKtApi
public fun classpathFromCurrentJar(packageName: String = ""): Collection<File> {
    return ClassLoader.getSystemClassLoader()
        .getResourceAsStream(packageName.replace("[.]", "/"))
        .reader()
        .readLines()
        .map { Class.forName("$packageName.${it.substring(0, it.lastIndexOf("."))}") }
        .flatMap { classpathFromClassOrThrow(it.kotlin) }
}