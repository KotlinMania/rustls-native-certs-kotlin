// port-lint: ignore (ported openssl-probe 0.2.0 dependency surface used by unix.rs)
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

internal data class OpensslProbeResult(
    val certFile: String?,
    val certDir: List<String>,
)

internal object OpensslProbe {
    /** Probe the current system for the certificate file and directory values OpenSSL expects. */
    fun probe(): OpensslProbeResult {
        val result = fromEnv()
        val certFile = result.certFile ?: CERTIFICATE_FILE_NAMES.firstOrNull { pathExists(it) }
        val certDir = result.certDir + candidateCertDirs()
        return OpensslProbeResult(certFile = certFile, certDir = certDir)
    }

    /** Probe known system locations for directories that likely contain CA certificates. */
    fun candidateCertDirs(): List<String> =
        CERTIFICATE_DIRS.filter { pathExists(it) }

    /** Check whether the OpenSSL certificate environment variables point at existing locations. */
    fun hasSslCertEnvVars(): Boolean {
        val result = fromEnv()
        return result.certFile != null || result.certDir.isNotEmpty()
    }

    private fun fromEnv(): OpensslProbeResult {
        val certFile = environmentVariable(ENV_CERT_FILE_NAME)
            ?.takeIf { pathExists(it) }
        val certDir = environmentVariable(ENV_CERT_DIR_NAME)
            ?.takeIf { pathExists(it) }
            ?.let(::listOf)
            .orEmpty()
        return OpensslProbeResult(certFile = certFile, certDir = certDir)
    }
}

private fun pathExists(path: String): Boolean =
    SystemFileSystem.metadataOrNull(Path(path)) != null

private val CERTIFICATE_DIRS = listOf(
    "/etc/ssl/certs",
    "/etc/pki/tls/certs",
)

private val CERTIFICATE_FILE_NAMES = listOf(
    "/etc/ssl/certs/ca-certificates.crt",
    "/etc/pki/ca-trust/extracted/pem/tls-ca-bundle.pem",
    "/etc/pki/tls/certs/ca-bundle.crt",
    "/etc/ssl/ca-bundle.pem",
    "/etc/pki/tls/cacert.pem",
    "/etc/ssl/cert.pem",
    "/opt/etc/ssl/certs/ca-certificates.crt",
)

private const val ENV_CERT_FILE_NAME = "SSL_CERT_FILE"
private const val ENV_CERT_DIR_NAME = "SSL_CERT_DIR"
