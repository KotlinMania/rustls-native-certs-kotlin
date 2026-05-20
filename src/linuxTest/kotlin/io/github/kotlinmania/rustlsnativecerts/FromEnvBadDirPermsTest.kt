// port-lint: source lib.rs
@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.Test
import kotlinx.cinterop.convert
import platform.posix.S_IRGRP
import platform.posix.S_IROTH
import platform.posix.S_IRUSR
import platform.posix.S_IWUSR
import platform.posix.S_IXGRP
import platform.posix.S_IXOTH
import platform.posix.S_IXUSR
import platform.posix.chmod

class FromEnvBadDirPermsTest {
    @Test
    fun fromEnvBadDirPerms() {
        val tempDir = createTempRoot("from-env-bad-dir-perms")
        try {
            chmod(tempDir.toString(), 0.convert())
            testCertPathsBadPerms(
                CertPaths(
                    file = null,
                    dirs = listOf(tempDir.toString()),
                ),
            )
        } finally {
            chmod(tempDir.toString(), readWriteExecuteMode().convert())
            deleteRecursively(tempDir)
        }
    }
}

private fun readWriteExecuteMode(): Int =
    S_IRUSR or S_IWUSR or S_IXUSR or S_IRGRP or S_IXGRP or S_IROTH or S_IXOTH
