package io.datalbry.plugin.semver.version

import org.eclipse.jgit.revwalk.RevCommit

class PreReleaseResolver {

    private val commitTimestamp: (tag: String, commit: RevCommit) -> String = { tag, commit ->
        tag.replace(COMMIT_TIMESTAMP, commit.commitTime.toString())
    }

    fun resolve(preRelease: String, lastCommit: RevCommit): String {
        val resolved = when {
            preRelease.contains(COMMIT_TIMESTAMP) -> commitTimestamp(preRelease, lastCommit)
            else -> throw IllegalArgumentException("Received a non valid pre-release: $preRelease")
        }
        return resolved
    }

    companion object {
        internal const val COMMIT_TIMESTAMP = "{COMMIT_TIMESTAMP}"
    }
}
