// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray

internal fun loadPemCerts(path: Path, out: CertificateResult) {
    if (SystemFileSystem.metadataOrNull(path) == null) {
        out.pemError(PemError.Io(PathLoadException(IoErrorKind.NotFound, "missing path")), path)
        return
    }

    val bytes = try {
        SystemFileSystem.source(path).buffered().use { source -> source.readByteArray() }
    } catch (err: IOException) {
        out.pemError(PemError.Io(err), path)
        return
    } catch (err: IllegalArgumentException) {
        out.pemError(PemError.Io(err), path)
        return
    }

    when (val result = CertificateDer.pemSliceIter(bytes)) {
        is PemDecodeResult.Ok -> out.certs.addAll(result.certificates)
        is PemDecodeResult.Err -> out.pemError(result.error, path)
    }
}
