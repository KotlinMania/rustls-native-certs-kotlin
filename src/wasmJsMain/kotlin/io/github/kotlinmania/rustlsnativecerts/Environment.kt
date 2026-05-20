// port-lint: ignore (Wasm-JS environment access for SSL_CERT_* discovery)
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.rustlsnativecerts

internal actual fun environmentVariable(name: String): String? =
    jsEnvironmentVariable(name)?.takeIf { it.isNotEmpty() }

private fun jsEnvironmentVariable(name: String): String? = js(
    "(typeof process !== 'undefined' && process && process.env && typeof process.env[name] === 'string') ? process.env[name] : null",
)
