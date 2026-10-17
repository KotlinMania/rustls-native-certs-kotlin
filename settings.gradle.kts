pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" }

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "rustls-native-certs-kotlin"

// Workspace composite: the io.github.kotlinmania:rustls-pki-types sibling
// lives next to this repo. When a checkout of the sibling is present
// alongside (i.e. the kotlinmania workspace layout that every developer has locally),
// use it via Gradle's composite-build substitution so `./gradlew build` resolves
// without a Maven publish.
val rustlsPkiTypesLocal = file("../rustls-pki-types-kotlin")
if (rustlsPkiTypesLocal.exists()) {
    includeBuild(rustlsPkiTypesLocal) {
        dependencySubstitution {
            substitute(module("io.github.kotlinmania:rustls-pki-types"))
                .using(project(":"))
            substitute(module("io.github.kotlinmania:rustls-pki-types-kotlin"))
                .using(project(":"))
        }
    }
}
