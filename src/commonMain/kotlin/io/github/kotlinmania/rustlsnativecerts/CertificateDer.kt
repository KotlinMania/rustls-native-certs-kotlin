// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

/** DER-encoded X.509 certificate. */
class CertificateDer(bytes: ByteArray) : Comparable<CertificateDer> {
    private val bytes: ByteArray = bytes.copyOf()

    fun asByteArray(): ByteArray = bytes.copyOf()

    override fun compareTo(other: CertificateDer): Int {
        val min = minOf(bytes.size, other.bytes.size)
        for (index in 0 until min) {
            val left = bytes[index].toInt() and 0xff
            val right = other.bytes[index].toInt() and 0xff
            if (left != right) return left - right
        }
        return bytes.size - other.bytes.size
    }

    override fun equals(other: Any?): Boolean =
        other is CertificateDer && bytes.contentEquals(other.bytes)

    override fun hashCode(): Int = bytes.contentHashCode()

    override fun toString(): String = "CertificateDer(${bytes.size} bytes)"

    companion object {
        /** Create a [CertificateDer] from DER bytes. */
        fun from(bytes: ByteArray): CertificateDer = CertificateDer(bytes)

        /** Iterate over certificate sections from PEM bytes. */
        internal fun pemSliceIter(pem: ByteArray): PemDecodeResult = decodePemCertificates(pem)
    }
}
