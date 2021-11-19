package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.git.util.gitTest
import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PreReleaseResolverTest {

    private val resolver = PreReleaseResolver()

    @Test
    fun resolvePreRelease_withCommitTimestamp_returnsResolvedTag() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1, 0, 0),
    ) { git ->
        val preReleaseIdentifier = "beta"
        val preRelease = "${preReleaseIdentifier}.${git.getHead().commitTime}"
        val result = resolver.resolve("beta.${PreReleaseResolver.COMMIT_TIMESTAMP}", git.getHead())
        assertEquals(result, preRelease)
    }

    @Test
    fun resolvePreRelease_withInvalidFormat_throwsIllegalArgument() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1, 0, 0),
    ) { git ->
        val preReleaseIdentifier = "beta"
        val preRelease = "${preReleaseIdentifier}.invalid"

        assertThrows<IllegalArgumentException> {
            resolver.resolve(preRelease, git.getHead())
        }
    }
}
