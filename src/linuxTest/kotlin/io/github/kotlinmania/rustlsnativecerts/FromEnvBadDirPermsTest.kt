package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.libc.unix.chmod
import io.github.kotlinmania.libc.unix.linuxlike.S_IRGRP
import io.github.kotlinmania.libc.unix.linuxlike.S_IROTH
import io.github.kotlinmania.libc.unix.linuxlike.S_IRUSR
import io.github.kotlinmania.libc.unix.linuxlike.S_IWUSR
import io.github.kotlinmania.libc.unix.linuxlike.S_IXGRP
import io.github.kotlinmania.libc.unix.linuxlike.S_IXOTH
import io.github.kotlinmania.libc.unix.linuxlike.S_IXUSR
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
    S_IRUSR or S_IWUSR or S_IXUSR or S_IRGRP or S_IXGRP or S_IROTH or S_IXOTH
