package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FromEnvMissingFileTest {
    @Test
    fun fromEnvMissingFile() {
        val result = emptyCertificateResult()
        loadPemCerts(Path("no/such/file"), result)
        val kind = assertIs<ErrorKind.Io>(firstError(result).kind)
        assertEquals(IoErrorKind.NotFound, kind.errorKind)
    }
}
