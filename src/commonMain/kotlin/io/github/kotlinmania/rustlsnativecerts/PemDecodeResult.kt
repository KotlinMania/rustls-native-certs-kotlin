// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

internal sealed class PemDecodeResult {
    data class Ok(val certificates: List<CertificateDer>) : PemDecodeResult()
    data class Err(val error: PemError) : PemDecodeResult()
}
