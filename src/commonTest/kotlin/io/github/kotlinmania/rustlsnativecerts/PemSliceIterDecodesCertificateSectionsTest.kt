// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PemSliceIterDecodesCertificateSectionsTest {
    @Test
    fun pemSliceIterDecodesCertificateSections() {
        val result = CertificateDer.pemSliceIter(
            """
            -----BEGIN CERTIFICATE-----
            Zm9v
            -----END CERTIFICATE-----
            -----BEGIN CERTIFICATE-----
            YmFy
            -----END CERTIFICATE-----
            """.trimIndent().encodeToByteArray(),
        )

        val certs = assertIs<PemDecodeResult.Ok>(result).certificates
        assertEquals(2, certs.size)
        assertContentEquals("foo".encodeToByteArray(), certs[0].asByteArray())
        assertContentEquals("bar".encodeToByteArray(), certs[1].asByteArray())
    }
}
