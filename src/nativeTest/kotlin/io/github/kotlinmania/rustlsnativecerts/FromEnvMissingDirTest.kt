// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlinx.io.files.Path

class FromEnvMissingDirTest {
    @Test
    fun fromEnvMissingDir() {
        val result = CertificateResult()
        loadPemCertsFromDir(Path("no/such/directory"), result)
        val kind = assertIs<ErrorKind.Io>(firstError(result).kind)
        assertEquals(IoErrorKind.NotFound, kind.errorKind)
    }
}
