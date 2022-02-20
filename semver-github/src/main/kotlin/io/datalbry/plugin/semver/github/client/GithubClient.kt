package io.datalbry.plugin.semver.github.client

import io.datalbry.plugin.semver.notes.ReleaseNotes
import io.datalbry.plugin.semver.version.model.SemanticVersion

interface GithubClient {

    fun createRelease(version: SemanticVersion, notes: ReleaseNotes)

}
