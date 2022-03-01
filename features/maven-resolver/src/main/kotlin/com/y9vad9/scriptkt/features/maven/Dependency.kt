package com.y9vad9.scriptkt.features.maven

@Target(AnnotationTarget.FILE)
@Repeatable
annotation class Dependency(val coordinates: String)