// port-lint: ignore (Wasm-WASI exposes no process environment through this port yet)
package io.github.kotlinmania.rustlsnativecerts

internal actual fun environmentVariable(name: String): String? = null
