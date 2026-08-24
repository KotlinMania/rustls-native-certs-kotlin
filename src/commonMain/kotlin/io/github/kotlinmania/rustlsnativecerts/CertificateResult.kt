// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path

internal fun emptyCertificateResult(): CertificateResult =
    CertificateResult(emptyList(), emptyList())

/** Results from trying to load certificates from the platform's native store. */
class CertificateResult(
    certs: List<CertificateDer> = emptyList(),
    errors: List<CertError> = emptyList(),
) {
    private val _certs: MutableList<CertificateDer> = certs.toMutableList()
    private val _errors: MutableList<CertError> = errors.toMutableList()

    /** Any certificates that were successfully loaded. */
    val certs: List<CertificateDer>
        get() = _certs

    /** Any errors encountered while loading certificates. */
    val errors: List<CertError>
        get() = _errors

    internal fun addCert(cert: CertificateDer) {
        _certs.add(cert)
    }

    internal fun addCerts(certs: Collection<CertificateDer>) {
        _certs.addAll(certs)
    }

    internal fun sortAndDeduplicateCerts() {
        _certs.sort()
        _certs.deduplicateInPlace()
    }

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
        _errors.add(
            CertError(
                context = "failed to read PEM from file",
                kind = when (err) {
                    is PemError.Io -> ErrorKind.Io(err.cause, path)
                    else -> ErrorKind.Pem(err)
                },
            ),
        )
    }

    internal fun ioError(err: Throwable, path: Path, context: String) {
        _errors.add(
            CertError(
                context = context,
                kind = ErrorKind.Io(err, path),
            ),
        )
    }

    internal fun osError(err: Throwable, context: String) {
        _errors.add(
            CertError(
                context = context,
                kind = ErrorKind.Os(err),
            ),
        )
    }
}

private fun MutableList<CertificateDer>.deduplicateInPlace() {
    if (size < 2) return
    var writeIndex = 1
    for (readIndex in 1 until size) {
        if (this[readIndex] != this[writeIndex - 1]) {
            this[writeIndex] = this[readIndex]
            writeIndex += 1
        }
    }
    while (size > writeIndex) {
        removeAt(lastIndex)
    }
}
