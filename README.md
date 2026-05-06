# rustls-native-certs-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Frustls--native--certs--kotlin-blue.svg)](https://github.com/KotlinMania/rustls-native-certs-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/rustls-native-certs-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/rustls-native-certs-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/rustls-native-certs-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/rustls-native-certs-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`rustls/rustls-native-certs`](https://github.com/rustls/rustls-native-certs).

**Original Project:** This port is based on [`rustls/rustls-native-certs`](https://github.com/rustls/rustls-native-certs). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `rustls/rustls-native-certs`

> The text below is reproduced and lightly edited from [`https://github.com/rustls/rustls-native-certs`](https://github.com/rustls/rustls-native-certs). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

![Logo](https://raw.githubusercontent.com/rustls/rustls/main/admin/rustls-logo-web.png)

**rustls-native-certs** allows [rustls](https://github.com/rustls/rustls) to use the
platform's native certificate store when operating as a TLS client.

> [!IMPORTANT]
> Instead of this crate, we suggest using [rustls-platform-verifier](https://github.com/rustls/rustls-platform-verifier),
> which provides a more robust solution with a simpler API. This crate is still maintained,
> but mostly for use inside the platform verifier on platforms where no other
> solution is available. For more context, see
> [deployment considerations](https://github.com/rustls/rustls-platform-verifier?tab=readme-ov-file#deployment-considerations).

## Status
rustls-native-certs is mature and widely used.

If you'd like to help out, please see [CONTRIBUTING.md](https://github.com/rustls/rustls-native-certs/blob/HEAD/CONTRIBUTING.md).

[![rustls](https://github.com/rustls/rustls-native-certs/actions/workflows/rust.yml/badge.svg)](https://github.com/rustls/rustls-native-certs/actions/workflows/rust.yml)
[![Documentation](https://docs.rs/rustls-native-certs/badge.svg)](https://docs.rs/rustls-native-certs)

Release notes can be found [on GitHub](https://github.com/rustls/rustls-native-certs/releases).

# API

This library exposes a single function with this signature:

```rust
pub fn load_native_certs() -> CertificateResult
```

This returns a `CertificateResult` which contains `certs: Vec<CertificateDer<'static>>` loaded with a
snapshot of the root certificates found on this platform along with any platform-specific errors.

This function can be expensive: on some platforms it involves loading
and parsing a ~300KB disk file.  It's therefore prudent to call
this sparingly.

# Platform support

This is supported on Windows, macOS and Linux:

- On all platforms, the `SSL_CERT_FILE` environment variable is checked first.
  If that's set, certificates are loaded from the path specified by that variable,
  or an error is returned if certificates cannot be loaded from the given path.
  If it's not set, then the platform-specific certificate source is used.
- On Windows, certificates are loaded from the system certificate store.
  The [`schannel`](https://github.com/steffengy/schannel-rs) crate is used to access
  the Windows certificate store APIs.
- On macOS, certificates are loaded from the keychain.
  The user, admin and system trust settings are merged together as documented
  by Apple.  The [`security-framework`](https://github.com/kornelski/rust-security-framework)
  crate is used to access the keystore APIs.
- On Linux and other UNIX-like operating systems, the
  [`openssl-probe`](https://github.com/alexcrichton/openssl-probe) crate is used to discover
  the filename of the system CA bundle.

# Worked example

See [`examples/google.rs`](https://github.com/rustls/rustls-native-certs/blob/HEAD/examples/google.rs).

# License

rustls-native-certs is distributed under the following three licenses:

- Apache License version 2.0.
- MIT license.
- ISC license.

These are included as LICENSE-APACHE, LICENSE-MIT and LICENSE-ISC
respectively.  You may use this software under the terms of any
of these licenses, at your option.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:rustls-native-certs-kotlin:0.1.0-SNAPSHOT")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same Apache-2.0 license as the upstream [`rustls/rustls-native-certs`](https://github.com/rustls/rustls-native-certs). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the rustls-native-certs authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`rustls/rustls-native-certs`](https://github.com/rustls/rustls-native-certs) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
