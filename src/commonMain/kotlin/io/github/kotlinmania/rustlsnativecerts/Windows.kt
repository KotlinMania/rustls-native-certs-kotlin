// port-lint: source windows.rs
package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.rustlspkitypes.CertificateDer
import io.github.kotlinmania.rustlspkitypes.Der

internal sealed class ValidUses {
    data object All : ValidUses()

    data class Oids(
        val strs: List<String>,
    ) : ValidUses()
}

internal object Windows {
    const val PKIX_SERVER_AUTH: String = "1.3.6.1.5.5.7.3.1"

    fun loadNativeCerts(): CertificateResult {
        val result = CertificateResult()
        val certs = openCurrentUserRootCerts(result) ?: return result
        for ((der, uses, isValid) in certs) {
            if (usableForRustls(uses) && isValid) {
                result.addCert(CertificateDer(Der(der)))
            }
        }
        return result
    }

    fun usableForRustls(uses: ValidUses): Boolean =
        when (uses) {
            is ValidUses.All -> true
            is ValidUses.Oids -> uses.strs.any { it == PKIX_SERVER_AUTH }
        }

    private fun openCurrentUserRootCerts(
        result: CertificateResult,
    ): List<Triple<ByteArray, ValidUses, Boolean>>? = emptyList()
}
