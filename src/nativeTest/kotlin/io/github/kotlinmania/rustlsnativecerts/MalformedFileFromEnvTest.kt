// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.io.files.Path

class MalformedFileFromEnvTest {
    @Test
    fun malformedFileFromEnv() {
        val tempDir = createTempRoot("malformed-file-from-env")
        try {
            val malformed = Path(tempDir.toString(), "malformed.kt")
            writeTextFile(malformed, "package nope\nfun thisIsNotACertificate() = Unit\n")

            val result = CertificateResult()
            loadPemCerts(malformed, result)
            assertEquals(0, result.certs.size)
            assertTrue(result.errors.isEmpty())
        } finally {
            deleteRecursively(tempDir)
        }
    }
}
