package io.github.kotlinmania.rustlsnativecerts

internal actual fun environmentVariable(name: String): String? =
    System.getenv(name)?.takeIf { it.isNotEmpty() }
