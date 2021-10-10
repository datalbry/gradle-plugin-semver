package io.datalbry.plugin.semver.git

import io.datalbry.plugin.semver.version.model.SemanticVersion
import org.eclipse.jgit.revwalk.RevCommit

data class SemanticGitTag(
    val commit: RevCommit,
    val version: SemanticVersion
)
