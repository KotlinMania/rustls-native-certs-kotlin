// port-lint: source lib.rs
package io.github.kotlinmania.rustlsnativecerts

import kotlinx.io.files.Path

/** Error while loading certificates. */
data class Error(
    val context: String,
    val kind: ErrorKind,
) {
    fun source(): Any = when (val errorKind = kind) {
        is ErrorKind.Io -> errorKind.inner
        is ErrorKind.Os -> errorKind.inner
        is ErrorKind.Pem -> errorKind.inner
    }

    override fun toString(): String =
        "$context: $kind"
}

/** Kind of certificate-loading error. */
sealed class ErrorKind {
    data class Io(
        val inner: Throwable,
        val path: Path,
        val errorKind: IoErrorKind = inferIoErrorKind(inner),
    ) : ErrorKind() {
        override fun toString(): String = "${inner.message ?: inner.toString()} at '$path'"
    }

    data class Os(val inner: Throwable) : ErrorKind() {
        override fun toString(): String = inner.message ?: inner.toString()
    }

    data class Pem(val inner: PemError) : ErrorKind() {
        override fun toString(): String = inner.toString()
    }
}

enum class IoErrorKind {
    NotFound,
    PermissionDenied,
    Other,
}

internal class PathLoadException(
    val errorKind: IoErrorKind,
    message: String,
) : Exception(message)

private fun inferIoErrorKind(error: Throwable): IoErrorKind {
    if (error is PathLoadException) return error.errorKind
    val text = listOfNotNull(error.message, error.cause?.message).joinToString(" ").lowercase()
    return when {
        "permission denied" in text || "access denied" in text -> IoErrorKind.PermissionDenied
        "no such file" in text || "missing path" in text -> IoErrorKind.NotFound
        else -> IoErrorKind.Other
    }
}
