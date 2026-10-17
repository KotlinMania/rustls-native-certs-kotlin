package io.github.kotlinmania.rustlsnativecerts

internal const val ENV_CERT_FILE: String = "SSL_CERT_FILE"
internal const val ENV_CERT_DIR: String = "SSL_CERT_DIR"

/** Certificate paths from `SSL_CERT_FILE` and/or `SSL_CERT_DIR`. */
internal data class CertPaths(
    val file: String?,
    val dirs: List<String>,
) {
    companion object {
        fun fromEnv(): CertPaths =
            CertPaths(
                file = environmentVariable(ENV_CERT_FILE),
                dirs = splitEnvPaths(environmentVariable(ENV_CERT_DIR)),
            )
    }

    /** Load certificates from the paths. */
    fun load(): CertificateResult = loadCertsFromPathsInternal(file, dirs)
}
