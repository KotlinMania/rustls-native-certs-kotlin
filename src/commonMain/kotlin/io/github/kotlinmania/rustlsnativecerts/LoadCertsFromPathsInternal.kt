// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path

internal fun loadCertsFromPathsInternal(file: String?, dirs: List<String>): CertificateResult {
    val out = emptyCertificateResult()
    if (file == null && dirs.isEmpty()) return out

    if (file != null) {
        loadPemCerts(Path(file), out)
    }

    for (dir in dirs) {
        loadPemCertsFromDir(Path(dir), out)
    }

    out.sortAndDeduplicateCerts()
    return out
}
