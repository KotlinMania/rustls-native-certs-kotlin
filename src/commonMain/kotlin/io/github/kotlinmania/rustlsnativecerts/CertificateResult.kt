package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.rustlspkitypes.CertificateDer
import kotlinx.io.files.Path

internal fun emptyCertificateResult(): CertificateResult =
    CertificateResult(emptyList(), emptyList())

/** Results from trying to load certificates from the platform's native store. */
class CertificateResult(
    certs: List<CertificateDer> = emptyList(),
    errors: List<Error> = emptyList(),
) {
    private val _certs: MutableList<CertificateDer> = certs.toMutableList()
    private val _errors: MutableList<Error> = errors.toMutableList()

    /** Any certificates that were successfully loaded. */
    val certs: List<CertificateDer>
        get() = _certs

    /** Any errors encountered while loading certificates. */
    val errors: List<Error>
        get() = _errors

    internal fun addCert(cert: CertificateDer) {
        _certs.add(cert)
    }

    internal fun addCerts(certs: Collection<CertificateDer>) {
        _certs.addAll(certs)
    }

    internal fun sortAndDeduplicateCerts() {
        _certs.sortWith(CERTIFICATE_DER_COMPARATOR)
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

    internal fun pemError(err: Throwable, path: Path) {
        val kind =
            when (err) {
                is PathLoadException,
                is kotlinx.io.IOException,
                is io.github.kotlinmania.rustlspkitypes.PemError.Io,
                -> {
                    ErrorKind.Io(
                        inner = err,
                        path = path,
                    )
                }

                else -> {
                    ErrorKind.Pem(err)
                }
            }
        _errors.add(
            Error(
                context = "failed to read PEM from file",
                kind = kind,
            ),
        )
    }

    internal fun ioError(err: Throwable, path: Path, context: String) {
        _errors.add(
            Error(
                context = context,
                kind = ErrorKind.Io(err, path),
            ),
        )
    }

    internal fun osError(err: Throwable, context: String) {
        _errors.add(
            Error(
                context = context,
                kind = ErrorKind.Os(err),
            ),
        )
    }
}

private val CERTIFICATE_DER_COMPARATOR =
    Comparator<CertificateDer> { a, b ->
        val aBytes = a.asRef()
        val bBytes = b.asRef()
        val minLen = minOf(aBytes.size, bBytes.size)
        for (i in 0 until minLen) {
            val diff = (aBytes[i].toInt() and 0xFF) - (bBytes[i].toInt() and 0xFF)
            if (diff != 0) return@Comparator diff
        }
        aBytes.size - bBytes.size
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
