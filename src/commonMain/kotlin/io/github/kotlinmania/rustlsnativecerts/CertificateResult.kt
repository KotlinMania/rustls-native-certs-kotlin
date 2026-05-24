// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path

internal fun emptyCertificateResult(): CertificateResult =
    CertificateResult(mutableListOf<CertificateDer>(), mutableListOf<Error>())

/** Results from trying to load certificates from the platform's native store. */
class CertificateResult(
    /** Any certificates that were successfully loaded. */
    val certs: MutableList<CertificateDer>,
    /** Any errors encountered while loading certificates. */
    val errors: MutableList<Error>,
) {
    /** Return the found certificates if no error occurred, otherwise throw. */
    fun expect(msg: String): List<CertificateDer> {
        if (errors.isEmpty()) return certs
        throw IllegalStateException("$msg: $errors")
    }

    /** Return the found certificates if no error occurred, otherwise throw. */
    fun unwrap(): List<CertificateDer> {
        if (errors.isEmpty()) return certs
        throw IllegalStateException("errors occurred while loading certificates: $errors")
    }

    internal fun pemError(err: PemError, path: Path) {
        errors.add(
            Error(
                context = "failed to read PEM from file",
                kind = when (err) {
                    is PemError.Io -> ErrorKind.Io(err.cause, path)
                    else -> ErrorKind.Pem(err)
                },
            ),
        )
    }

    internal fun ioError(err: Throwable, path: Path, context: String) {
        errors.add(
            Error(
                context = context,
                kind = ErrorKind.Io(err, path),
            ),
        )
    }

    internal fun osError(err: Throwable, context: String) {
        errors.add(
            Error(
                context = context,
                kind = ErrorKind.Os(err),
            ),
        )
    }
}
