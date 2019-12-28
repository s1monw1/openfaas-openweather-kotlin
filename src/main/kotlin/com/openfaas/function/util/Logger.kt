package com.openfaas.function.util

import java.time.Clock

fun logger(message: () -> Any) =
    println("[${Thread.currentThread().name.takeLast(40)}] ${Clock.systemUTC().instant()} ${message()}")
