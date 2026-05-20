// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

internal fun firstError(result: CertificateResult): Error =
    result.errors.first()
