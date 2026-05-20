// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

internal fun splitEnvPaths(value: String?): List<String> {
    if (value.isNullOrEmpty()) return emptyList()
    val separator = if (';' in value) ';' else ':'
    return value.split(separator).filter { it.isNotEmpty() }
}
