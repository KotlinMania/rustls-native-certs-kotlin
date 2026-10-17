package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FromEnvWithNonRegularAndEmptyFileTest {
    @Test
    fun fromEnvWithNonRegularAndEmptyFile() {
        val result = emptyCertificateResult()
        loadPemCerts(Path("/dev/null"), result)
        assertEquals(0, result.certs.size)
        assertTrue(result.errors.isEmpty())
    }
}
