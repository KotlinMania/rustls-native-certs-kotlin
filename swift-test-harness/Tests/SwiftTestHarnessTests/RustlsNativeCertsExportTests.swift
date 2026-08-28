import Testing
import RustlsNativeCerts

@Suite("RustlsNativeCerts Swift Export Tests")
struct RustlsNativeCertsExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() {
        #expect(Bool(true), "RustlsNativeCerts swift module imported cleanly")
    }
}

