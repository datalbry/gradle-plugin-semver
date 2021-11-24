package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.git.util.gitTest
import io.datalbry.plugin.semver.version.PreReleaseTemplateResolver.Companion.COMMIT_TIMESTAMP
import io.datalbry.plugin.semver.version.PreReleaseTemplateResolver.Companion.BUILD_TIMESTAMP
import io.datalbry.plugin.semver.version.model.SemanticVersion
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PreReleaseTemplateResolverTest {

    private val resolver = PreReleaseTemplateResolver()

    @Test
    fun resolvePreRelease_withCommitTimestamp_returnsResolvedTag() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1, 0, 0),
    ) { git ->
        val preReleaseTemplate = "beta.${COMMIT_TIMESTAMP}"
        val expected = "beta.${git.getHead().commitTime}"
        val result = resolver.resolve(preReleaseTemplate, git.getHead())
        assertEquals(expected, result)
    }

    @Test
    fun resolvePreRelease_withBuildTimestamp_returnsResolvedTag() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1, 0, 0),
    ) { git ->
        val buildTime = OffsetDateTime.now()
        val preReleaseTemplate = "beta.$BUILD_TIMESTAMP"
        val expected = "beta.${buildTime.toEpochSecond()}"
        val result = resolver.resolve(preReleaseTemplate, git.getHead(), buildTime)
        assertEquals(expected, result)
    }

    @Test
    fun resolvePreRelease_withBuildTimestampAndCommitTimestamp_returnsResolvedTag() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1, 0, 0),
    ) { git ->
        val buildTime = OffsetDateTime.now()
        val preReleaseTemplate = "beta.${BUILD_TIMESTAMP}.${COMMIT_TIMESTAMP}"
        val expected = "beta.${buildTime.toEpochSecond()}.${git.getHead().commitTime}"
        val result = resolver.resolve(preReleaseTemplate, git.getHead(), buildTime)
        assertEquals(expected, result)
    }
}
