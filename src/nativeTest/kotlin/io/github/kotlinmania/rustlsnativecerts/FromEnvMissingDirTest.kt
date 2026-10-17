package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FromEnvMissingDirTest {
    @Test
    fun fromEnvMissingDir() {
        val result = emptyCertificateResult()
        loadPemCertsFromDir(Path("no/such/directory"), result)
        val kind = assertIs<ErrorKind.Io>(firstError(result).kind)
        assertEquals(IoErrorKind.NotFound, kind.errorKind)
    }
}
