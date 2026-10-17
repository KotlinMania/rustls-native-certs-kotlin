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

class FromEnvBadDirPermsTest {
    @Test
    fun fromEnvBadDirPerms() {
        val tempDir = createTempRoot("from-env-bad-dir-perms")
        try {
            chmod(tempDir.toString(), 0u)
            testCertPathsBadPerms(
                CertPaths(
                    file = null,
                    dirs = listOf(tempDir.toString()),
                ),
            )
        } finally {
            chmod(tempDir.toString(), readWriteExecuteMode())
            deleteRecursively(tempDir)
        }
    }
}

private fun readWriteExecuteMode(): UInt =
    (S_IRUSR.toUInt() or S_IWUSR.toUInt() or S_IXUSR.toUInt() or S_IRGRP.toUInt() or S_IXGRP.toUInt() or S_IROTH.toUInt() or S_IXOTH.toUInt())
