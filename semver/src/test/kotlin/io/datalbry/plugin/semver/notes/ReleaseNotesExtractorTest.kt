package io.datalbry.plugin.semver.notes

import io.datalbry.plugin.semver.git.util.gitTest
import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReleaseNotesExtractorTest {

    private val fixture = ReleaseNotesExtractor()

    @Test
    fun extractReleaseNotes_noMessage_excludesLastReleaseCommit() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1,0,0),
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!
        val commits = git.getCommits(latestVersion.commit).map { it.fullMessage }
        val releaseNotes = fixture.extractReleaseNotes(commits)
        assertTrue(releaseNotes.messages.isEmpty())
    }

    @Test
    fun extractReleaseNotes_singleMessage_contextMatches() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1,0,0),
        "fix(context-two): secondCommit" to null,
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!
        val commits = git.getCommits(latestVersion.commit).map { it.fullMessage }
        val releaseNotes = fixture.extractReleaseNotes(commits)
        assertTrue(releaseNotes.messages.size == 1)
        assertEquals("fix", releaseNotes.messages.first().type)
    }

    @Test
    fun extractReleaseNotes_singleMessage_typeMatches() = gitTest(
        "fix(context-one): firstCommit" to SemanticVersion(1,0,0),
        "fix(context-two): secondCommit" to null,
    ) { git ->
        val latestVersion = git.getLatestFullVersion()!!
        val commits = git.getCommits(latestVersion.commit).map { it.fullMessage }
        val releaseNotes = fixture.extractReleaseNotes(commits)
        assertTrue(releaseNotes.messages.size == 1)
        assertEquals("context-two", releaseNotes.messages.first().context)
    }
}
