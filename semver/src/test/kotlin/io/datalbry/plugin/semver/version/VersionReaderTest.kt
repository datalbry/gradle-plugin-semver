package io.datalbry.plugin.semver.version

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals

class VersionReaderTest {

    private val reader = VersionReader()

    @Test
    fun read_validVersion_worksFine() {
        val validVersion = "1.2.3"
        val semanticVersion = reader.readVersion(validVersion)
        assertEquals(semanticVersion.major, 1)
        assertEquals(semanticVersion.minor, 2)
        assertEquals(semanticVersion.patch, 3)
    }

    @Test
    fun read_invalidVersion_throwsIllegalArgument() {
        val invalidVersion = "1.1.2.3"
        assertThrows<IllegalArgumentException> { reader.readVersion(invalidVersion) }
    }

    @Test
    fun read_invalidVersion_withPrefix_throwsIllegalArgument() {
        val invalidVersion = "v1.1.2"
        assertThrows<IllegalArgumentException> { reader.readVersion(invalidVersion) }
    }

    @Test
    fun read_invalidVersion_withNegativeVersions_throwsIllegalArgument() {
        val invalidVersion = "-1.-1.-1"
        assertThrows<IllegalArgumentException> { reader.readVersion(invalidVersion) }
    }

    @Test
    fun read_validVersion_withSuffix_worksFine() {
        val versionWithSuffix = "1.2.3-SNAPSHOT"
        val semanticVersion = reader.readVersion(versionWithSuffix)
        assertEquals(semanticVersion.major, 1)
        assertEquals(semanticVersion.minor, 2)
        assertEquals(semanticVersion.patch, 3)
        assertEquals(semanticVersion.preRelease, "SNAPSHOT")
    }
}
