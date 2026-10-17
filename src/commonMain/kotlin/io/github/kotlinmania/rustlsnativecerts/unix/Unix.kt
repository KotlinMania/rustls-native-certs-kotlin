// port-lint: source unix.rs
package io.github.kotlinmania.rustlsnativecerts.unix

import io.github.kotlinmania.rustlsnativecerts.CertPaths
import io.github.kotlinmania.rustlsnativecerts.CertificateResult
import io.github.kotlinmania.rustlsnativecerts.OpensslProbe

internal fun loadNativeCerts(): CertificateResult {
    val likelyLocations = OpensslProbe.probe()
    return CertPaths(
        file = likelyLocations.certFile,
        dirs = likelyLocations.certDir,
    ).load()
}
