// port-lint: ignore (native environment access for SSL_CERT_* discovery)
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.rustlsnativecerts

import kotlinx.cinterop.toKString

internal actual fun environmentVariable(name: String): String? =
    platform.posix.getenv(name)?.toKString()?.takeIf { it.isNotEmpty() }
