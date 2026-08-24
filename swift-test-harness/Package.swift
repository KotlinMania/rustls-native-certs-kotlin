// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "SwiftTestHarness",
    platforms: [
        .macOS(.v14)
    ],
    dependencies: [
        .package(name: "RustlsNativeCerts", path: "../build/SPMPackage/macosArm64/Debug")
    ],
    targets: [
        .executableTarget(
            name: "SwiftTestHarnessTests",
            dependencies: [
                .product(name: "RustlsNativeCertsLibrary", package: "RustlsNativeCerts")
            ],
            path: "Tests/SwiftTestHarnessTests",
            linkerSettings: [
                .unsafeFlags([
                    "-L", "../build/swift-test",
                    "-lRustlsNativeCerts",
                ]),
            ]
        ),
    ]
)

