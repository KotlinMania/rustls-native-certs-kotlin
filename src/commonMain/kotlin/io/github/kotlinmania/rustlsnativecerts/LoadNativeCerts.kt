// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

/**
 * Load root certificates found in the platform's native certificate store.
 *
 * ## Environment variables
 *
 * | Env. Var.     | Description                                                        |
 * |---------------|--------------------------------------------------------------------|
 * | SSL_CERT_FILE | File containing any number of certificates in PEM format.          |
 * | SSL_CERT_DIR  | Path-list of directories containing certificate files.              |
 *
 * If either or both are set, certificates are loaded only from those locations
 * and not the platform-native certificate store.
 */
fun loadNativeCerts(): CertificateResult {
    val paths = CertPaths.fromEnv()
    return when {
        paths.dirs.isNotEmpty() -> paths.load()
        paths.file != null -> paths.load()
        else -> Unix.loadNativeCerts()
    }
}
