package io.datalbry.plugin.semver.git

import io.datalbry.plugin.semver.git.util.gitTest
import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import kotlin.io.path.ExperimentalPathApi

@OptIn(ExperimentalPathApi::class)
class GitGraphTest {

    @Test
    fun getLatestFullVersion_withOneVersion_returnsExactly() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1,0,0),
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!.version
        assertNotNull(latestVersion)
        assertEquals(latestVersion.major, 1)
        assertEquals(latestVersion.minor, 0)
        assertEquals(latestVersion.patch, 0)
    }

    @Test
    fun getLatestFullVersion_withoutAnyVersion_returnsNull() = gitTest(
        "fix(context-one): firstCommit" to null,
    ) { git ->
        val latestVersion = git.getLatestFullVersion()
        assertNull(latestVersion)
    }

    @Test
    fun getLatestFullVersion_withOnlyPreReleases_returnsNull() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(0,1,0, "RC.1"),
        "fix(context-one): secondCommit" to SemanticVersion(0,1,0, "RC.2"),
        "fix(context-one): thirdCommit" to SemanticVersion(0,1,0, "RC.3"),
    ) { git ->
        val latestVersion = git.getLatestFullVersion()
        assertNull(latestVersion)
    }

    @Test
    fun getLatestFullVersion_withPreReleaseAfterLatestRelease_returnsLatestRelease() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(0,1,0, "RC.1"),
        "fix(context-one): secondCommit" to SemanticVersion(0,1,0),
        "fix(context-one): thirdCommit" to SemanticVersion(0,1,1, "RC.3"),
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!.version
        assertEquals(latestVersion.major, 0)
        assertEquals(latestVersion.minor, 1)
        assertEquals(latestVersion.patch, 0)
    }

    @Test
    fun getLatestFullVersion_withCommitsAfterRelease_returnsLatestRelease() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(0,1,0, "RC.1"),
        "fix(context-one): secondCommit" to SemanticVersion(0,1,0),
        "fix(context-one): thirdCommit" to null,
        "fix(context-one): fourthCommit" to null,
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!.version
        assertEquals(latestVersion.major, 0)
        assertEquals(latestVersion.minor, 1)
        assertEquals(latestVersion.patch, 0)
    }

    @Test
    fun getLatestFullVersion_withNonConventionalCommits_logsAllInvalidLogs() = gitTest(
        "firstCommit" to SemanticVersion(0,1,0, "RC.1"),
        "secondCommit" to SemanticVersion(0,1,0),
        "thirdCommit" to null,
        "fourthCommit" to null,
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!.version
        assertEquals(latestVersion.major, 0)
        assertEquals(latestVersion.minor, 1)
        assertEquals(latestVersion.patch, 0)
    }

    @Test
    fun getLatestVersion_withOnlyPreReleases_returnsNull() = gitTest(
        "secondCommit" to null,
        "thirdCommit" to null,
        "firstCommit" to SemanticVersion(0,1,0, "0.1.0-dev.1644175890")
    ) { git ->
        val latestVersion = git.getLatestFullVersion()
        assertNull(latestVersion)
    }
}
