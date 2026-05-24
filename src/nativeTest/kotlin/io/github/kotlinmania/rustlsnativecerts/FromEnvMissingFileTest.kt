// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.io.files.Path

class FromEnvMissingFileTest {
    @Test
    fun fromEnvMissingFile() {
        val result = emptyCertificateResult()
        loadPemCerts(Path("no/such/file"), result)
        val kind = assertIs<ErrorKind.Io>(firstError(result).kind)
        assertEquals(IoErrorKind.NotFound, kind.errorKind)
    }
}
