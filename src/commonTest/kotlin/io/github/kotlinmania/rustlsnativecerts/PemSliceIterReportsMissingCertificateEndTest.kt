// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertIs

class PemSliceIterReportsMissingCertificateEndTest {
    @Test
    fun pemSliceIterReportsMissingCertificateEnd() {
        val result = CertificateDer.pemSliceIter(
            """
            -----BEGIN CERTIFICATE-----
            Zm9v
            """.trimIndent().encodeToByteArray(),
        )

        assertIs<PemDecodeResult.Err>(result)
        assertIs<PemError.MissingSectionEnd>(result.error)
    }
}
