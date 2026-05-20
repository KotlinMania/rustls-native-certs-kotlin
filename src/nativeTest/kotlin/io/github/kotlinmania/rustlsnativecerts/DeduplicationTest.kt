// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.io.files.Path

class DeduplicationTest {
    @Test
    fun deduplication() {
        val tempDir = createTempRoot("deduplication")
        try {
            val filePath = Path(tempDir.toString(), "ca-certificates.crt")
            val dirPath = tempDir

            writeTextFile(filePath, BADSSL_CERT_PEM + ONE_EXISTING_CA_PEM)
            writeTextFile(Path(dirPath.toString(), "71f3bb26.0"), BADSSL_CERT_PEM)
            writeTextFile(Path(dirPath.toString(), "912e7cd5.0"), ONE_EXISTING_CA_PEM)

            val fileResult = CertPaths(
                file = filePath.toString(),
                dirs = emptyList(),
            ).load()
            assertEquals(2, fileResult.certs.size)

            val dirResult = CertPaths(
                file = null,
                dirs = listOf(dirPath.toString()),
            ).load()
            assertEquals(2, dirResult.certs.size)

            val bothResult = CertPaths(
                file = filePath.toString(),
                dirs = listOf(dirPath.toString()),
            ).load()
            assertEquals(2, bothResult.certs.size)
        } finally {
            deleteRecursively(tempDir)
        }
    }
}
