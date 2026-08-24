// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

internal fun firstError(result: CertificateResult): CertError =
    result.errors.first()
