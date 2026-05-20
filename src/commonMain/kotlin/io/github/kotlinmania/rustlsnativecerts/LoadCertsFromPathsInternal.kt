// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path

internal fun loadCertsFromPathsInternal(file: String?, dirs: List<String>): CertificateResult {
    val out = CertificateResult()
    if (file == null && dirs.isEmpty()) return out

    if (file != null) {
        loadPemCerts(Path(file), out)
    }

    for (dir in dirs) {
        loadPemCertsFromDir(Path(dir), out)
    }

    out.certs.sort()
    out.certs.deduplicateInPlace()
    return out
}

private fun MutableList<CertificateDer>.deduplicateInPlace() {
    if (size < 2) return
    var writeIndex = 1
    for (readIndex in 1 until size) {
        if (this[readIndex] != this[writeIndex - 1]) {
            this[writeIndex] = this[readIndex]
            writeIndex += 1
        }
    }
    while (size > writeIndex) {
        removeAt(lastIndex)
    }
}
