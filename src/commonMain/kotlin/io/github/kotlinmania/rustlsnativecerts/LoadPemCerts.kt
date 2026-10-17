package io.github.kotlinmania.rustlsnativecerts

import io.github.kotlinmania.rustlspkitypes.CertificateDer
import io.github.kotlinmania.rustlspkitypes.Der
import io.github.kotlinmania.rustlspkitypes.PemObject
import io.github.kotlinmania.rustlspkitypes.SectionKind
import kotlinx.io.IOException
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray

internal fun loadPemCerts(path: Path, out: CertificateResult) {
    if (SystemFileSystem.metadataOrNull(path) == null) {
        out.pemError(PathLoadException(IoErrorKind.NotFound, "missing path"), path)
        return
    }

    val bytes = try {
        SystemFileSystem.source(path).buffered().use { source -> source.readByteArray() }
    } catch (err: IOException) {
        out.pemError(err, path)
        return
    } catch (err: IllegalArgumentException) {
        out.pemError(err, path)
        return
    }

    val iter = PemObject.pemSliceIter(bytes) { kind, der ->
        if (kind == SectionKind.Certificate) CertificateDer(Der(der)) else null
    }

    for (result in iter) {
        if (result.isSuccess) {
            out.addCert(result.getOrThrow())
        } else {
            out.pemError(result.exceptionOrNull()!!, path)
        }
    }
}
