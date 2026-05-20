// port-lint: ignore (platform environment access required by lib.rs and openssl-probe)
package io.github.kotlinmania.rustlsnativecerts

internal expect fun environmentVariable(name: String): String?
