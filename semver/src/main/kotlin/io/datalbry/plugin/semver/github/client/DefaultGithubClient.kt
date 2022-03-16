package io.datalbry.plugin.semver.github.client

import io.datalbry.plugin.semver.notes.ReleaseNotes
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter
import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.kohsuke.github.GitHub

class DefaultGithubClient(
    private val repository: String,
    token: String
): GithubClient {

    private val client = GitHub.connectUsingOAuth(token)

    override fun createRelease(version: SemanticVersion, notes: ReleaseNotes) {
        val body = MarkdownReleaseNotesFormatter.format(notes)
        client
            .getRepository(repository)
            .createRelease(version.toString())
            .prerelease(version.preRelease == null)
            .body(body)
            .create()
    }
}
