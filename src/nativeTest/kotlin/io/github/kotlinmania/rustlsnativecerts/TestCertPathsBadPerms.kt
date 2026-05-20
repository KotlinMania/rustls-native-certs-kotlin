// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.test.assertEquals
import kotlin.test.assertIs

internal fun testCertPathsBadPerms(certPaths: CertPaths) {
    val result = certPaths.load()

    if (certPaths.file == null && certPaths.dirs.isEmpty()) {
        error("only one of file or dir should be set")
    }

    val error = firstError(result)
    val kind = assertIs<ErrorKind.Io>(error.kind)
    assertEquals(IoErrorKind.PermissionDenied, kind.errorKind)
}
