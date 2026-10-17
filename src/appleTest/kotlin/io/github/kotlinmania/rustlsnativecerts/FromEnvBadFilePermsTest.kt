package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.libc.unix.bsd.apple.S_IRGRP
import io.github.kotlinmania.libc.unix.bsd.apple.S_IROTH
import io.github.kotlinmania.libc.unix.bsd.apple.S_IRUSR
import io.github.kotlinmania.libc.unix.bsd.apple.S_IWUSR
import io.github.kotlinmania.libc.unix.bsd.apple.S_IXGRP
import io.github.kotlinmania.libc.unix.bsd.apple.S_IXOTH
import io.github.kotlinmania.libc.unix.bsd.apple.S_IXUSR
import io.github.kotlinmania.libc.unix.chmod
import kotlin.test.Test
import kotlinx.io.files.Path

class FromEnvBadFilePermsTest {
    @Test
    fun fromEnvBadFilePerms() {
        val tempDir = createTempRoot("from-env-bad-file-perms")
        val filePath = Path(tempDir.toString(), "unreadable.pem")
        try {
            writeTextFile(filePath, "")
            chmod(filePath.toString(), 0u)
            testCertPathsBadPerms(
                CertPaths(
                    file = filePath.toString(),
                    dirs = emptyList(),
                ),
            )
        } finally {
            chmod(filePath.toString(), readWriteExecuteMode())
            deleteRecursively(tempDir)
        }
    }
}

private fun readWriteExecuteMode(): UInt =
    (S_IRUSR.toUInt() or S_IWUSR.toUInt() or S_IXUSR.toUInt() or S_IRGRP.toUInt() or S_IXGRP.toUInt() or S_IROTH.toUInt() or S_IXOTH.toUInt())
