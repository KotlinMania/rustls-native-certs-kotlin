// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

sealed class PemError {
    data class MissingSectionEnd(val endMarker: String) : PemError()
    data class IllegalSectionStart(val line: String) : PemError()
    data class Base64Decode(val message: String) : PemError()
    data class Io(val cause: Throwable) : PemError()
    data object NoItemsFound : PemError()
}
