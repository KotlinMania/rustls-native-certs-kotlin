// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.IOException
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/** Load certificates from a certificate directory. */
internal fun loadPemCertsFromDir(dir: Path, out: CertificateResult) {
    if (SystemFileSystem.metadataOrNull(dir) == null) {
        out.ioError(PathLoadException(IoErrorKind.NotFound, "missing path"), dir, "opening directory")
        return
    }

    val entries = try {
        SystemFileSystem.list(dir)
    } catch (err: IOException) {
        out.ioError(err, dir, "opening directory")
        return
    } catch (err: IllegalArgumentException) {
        out.ioError(err, dir, "opening directory")
        return
    }

    for (path in entries) {
        val metadata = try {
            SystemFileSystem.metadataOrNull(path)
        } catch (err: IOException) {
            out.ioError(err, path, "failed to open file")
            continue
        }

        if (metadata?.isRegularFile == true) {
            loadPemCerts(path, out)
        }
    }
}
