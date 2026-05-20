// port-lint: source unix.rs
package io.github.kotlinmania.rustlsnativecerts

internal object Unix {
    fun loadNativeCerts(): CertificateResult {
        val likelyLocations = OpensslProbe.probe()
        return CertPaths(
            file = likelyLocations.certFile,
            dirs = likelyLocations.certDir,
        ).load()
    }
}
