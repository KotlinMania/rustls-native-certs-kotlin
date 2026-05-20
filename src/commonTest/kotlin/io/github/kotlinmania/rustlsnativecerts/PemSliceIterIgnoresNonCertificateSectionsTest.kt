// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PemSliceIterIgnoresNonCertificateSectionsTest {
    @Test
    fun pemSliceIterIgnoresNonCertificateSections() {
        val result = CertificateDer.pemSliceIter(
            """
            not a certificate
            -----BEGIN PUBLIC KEY-----
            Zm9v
            -----END PUBLIC KEY-----
            """.trimIndent().encodeToByteArray(),
        )

        val certs = assertIs<PemDecodeResult.Ok>(result).certificates
        assertEquals(0, certs.size)
    }
}
