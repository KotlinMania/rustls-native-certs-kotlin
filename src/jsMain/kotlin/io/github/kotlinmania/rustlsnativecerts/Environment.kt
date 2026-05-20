// port-lint: ignore (JS environment access for SSL_CERT_* discovery)
package io.github.kotlinmania.rustlsnativecerts

internal actual fun environmentVariable(name: String): String? {
    val raw: dynamic = jsEnvironmentVariable(name)
    return if (raw == null || raw == undefined()) null else raw.unsafeCast<String>().takeIf { it.isNotEmpty() }
}

private fun jsEnvironmentVariable(name: String): dynamic = js(
    "(typeof process !== 'undefined' && process && process.env) ? process.env[name] : undefined",
)

private fun undefined(): dynamic = js("undefined")
