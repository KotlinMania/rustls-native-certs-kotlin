// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
internal fun decodePemCertificates(pem: ByteArray): PemDecodeResult {
    val certs = mutableListOf<CertificateDer>()
    var activeLabel: String? = null
    var activeKnown = false
    val body = StringBuilder()

    for (line in pem.decodeToString().lineSequence()) {
        if (line.startsWith("-----BEGIN ")) {
            val labelEnd = line.lastIndexOf("-----")
            if (labelEnd < 11 || line.substring(labelEnd) != "-----") {
                return PemDecodeResult.Err(PemError.IllegalSectionStart(line))
            }
            activeLabel = line.substring(11, labelEnd).trimEnd()
            activeKnown = activeLabel == PEM_CERTIFICATE_LABEL
            body.clear()
            continue
        }

        val label = activeLabel
        if (label != null && line.startsWith("-----END $label-----")) {
            if (activeKnown) {
                val encoded = body.toString().filterNot { it.isWhitespace() }
                val der = try {
                    Base64.Default.decode(encoded)
                } catch (err: IllegalArgumentException) {
                    return PemDecodeResult.Err(PemError.Base64Decode(err.message ?: err.toString()))
                }
                certs.add(CertificateDer.from(der))
            }
            activeLabel = null
            activeKnown = false
            body.clear()
            continue
        }

        if (label != null && activeKnown) {
            body.append(line)
        }
    }

    val label = activeLabel
    if (label != null && activeKnown) {
        return PemDecodeResult.Err(PemError.MissingSectionEnd("-----END $label-----"))
    }

    return PemDecodeResult.Ok(certs)
}
