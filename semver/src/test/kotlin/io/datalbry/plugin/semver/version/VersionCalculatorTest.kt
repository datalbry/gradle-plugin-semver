package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VersionCalculatorTest {

    private val versionCalculator = VersionCalculator()

    @Test
    fun calculateNextVersion_withoutLastVersion_returnsFirstVersion() {
        val commits = listOf(
            "fix(scope_1): some good fix"
        )
        val nextVersion = versionCalculator.calculateNextVersion(commits, null)

        assertEquals(nextVersion.major, 0)
        assertEquals(nextVersion.minor, 1)
        assertEquals(nextVersion.patch, 0)
    }

    @Test
    fun calculateNextVersion_withBreakingChange_raisesMajor() {
        val commits = listOf(
            "fix(scope_1): BREAKING CHANGE"
        )
        val lastVersion = SemanticVersion(0, 1,0)
        val nextVersion = versionCalculator.calculateNextVersion(commits, lastVersion)

        assertEquals(nextVersion.major, 1)
        assertEquals(nextVersion.minor, 0)
        assertEquals(nextVersion.patch, 0)
    }

    @Test
    fun calculateNextVersion_withFeature_raisesMinor() {
        val commits = listOf(
            "feat(scope_1): best feature"
        )
        val lastVersion = SemanticVersion(0, 1,0)
        val nextVersion = versionCalculator.calculateNextVersion(commits, lastVersion)

        assertEquals(nextVersion.major, 0)
        assertEquals(nextVersion.minor, 2)
        assertEquals(nextVersion.patch, 0)
    }

    @Test
    fun calculateNextVersion_withFix_raisesPatch() {
        val commits = listOf(
            "fix(scope_1): ugh, i hate bugs"
        )
        val lastVersion = SemanticVersion(0, 1,0)
        val nextVersion = versionCalculator.calculateNextVersion(commits, lastVersion)

        assertEquals(nextVersion.major, 0)
        assertEquals(nextVersion.minor, 1)
        assertEquals(nextVersion.patch, 1)
    }

    @Test
    fun calculateNextVersion_withExclamationMark_raisesMajor() {
        val commits = listOf(
            "fix(scope_1)!: ugh, i hate bugs"
        )
        val lastVersion = SemanticVersion(0, 1,0)
        val nextVersion = versionCalculator.calculateNextVersion(commits, lastVersion)

        assertEquals(nextVersion.major, 1)
        assertEquals(nextVersion.minor, 0)
        assertEquals(nextVersion.patch, 0)
    }

}
