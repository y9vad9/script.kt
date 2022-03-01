package com.y9vad9.scriptkt.imports

@Repeatable
@Target(AnnotationTarget.FILE)
annotation class ImportScript(vararg val names: String)
