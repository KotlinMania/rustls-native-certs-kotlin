// port-lint: source macos.rs
package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.rustlspkitypes.CertificateDer
import io.github.kotlinmania.rustlspkitypes.Der

internal enum class Domain {
    User,
    Admin,
    System,
}

internal enum class TrustSettingsForCertificate {
    TrustRoot,
    TrustAsRoot,
    Deny,
    Unspecified,
}

internal object Macos {
    fun loadNativeCerts(): CertificateResult {
        val result = CertificateResult()
        val allCerts = mutableMapOf<ByteArray, TrustSettingsForCertificate>()

        for (domain in listOf(Domain.User, Domain.Admin, Domain.System)) {
            val certsForDomain = loadDomainCerts(domain, result) ?: continue
            for ((der, trusted) in certsForDomain) {
                if (!allCerts.containsKey(der)) {
                    allCerts[der] = trusted
                }
            }
        }

        for ((der, trusted) in allCerts) {
            when (trusted) {
                TrustSettingsForCertificate.TrustRoot,
                TrustSettingsForCertificate.TrustAsRoot,
                -> {
                    result.addCert(CertificateDer(Der(der)))
                }

                else -> {}
            }
        }

        return result
    }

    private fun loadDomainCerts(
        domain: Domain,
        result: CertificateResult,
    ): List<Pair<ByteArray, TrustSettingsForCertificate>>? {
        val domainContext =
            when (domain) {
                Domain.User -> "failed to load user trust settings"
                Domain.Admin -> "failed to load admin trust settings"
                Domain.System -> "failed to load system trust settings"
            }
        return emptyList()
    }
}
