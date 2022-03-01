package com.y9vad9.scriptkt.annotation

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@RequiresOptIn("Unsafe arguments input. Be careful to avoid runtime errors.", level = RequiresOptIn.Level.WARNING)
public annotation class UnsafeArgumentsInput