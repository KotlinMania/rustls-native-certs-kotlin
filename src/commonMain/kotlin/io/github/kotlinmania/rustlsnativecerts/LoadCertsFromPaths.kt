// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

/**
 * Load certificates from the given paths.
 *
 * If both are `null`, returns an empty [CertificateResult].
 */
fun loadCertsFromPaths(file: String?, dir: String?): CertificateResult =
    loadCertsFromPathsInternal(file, listOfNotNull(dir))
