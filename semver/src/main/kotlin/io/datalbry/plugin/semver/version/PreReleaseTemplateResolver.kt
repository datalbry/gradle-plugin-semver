package io.datalbry.plugin.semver.version

import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import org.eclipse.jgit.revwalk.RevCommit
import java.lang.IllegalArgumentException

/**
 * [PreReleaseTemplateResolver] resolves the arbitrary templates
 */
class PreReleaseTemplateResolver {

    /**
     * Resolves the template strings.
     *
     * @param preReleaseTemplate to format accordingly
     * @param releaseCommit to derive information from
     * @param timestamp of the build
     *
     * @return the resolved pre-release
     */
    fun resolve(preReleaseTemplate: String, releaseCommit: RevCommit, timestamp: OffsetDateTime = now()): String {
        val message =  preReleaseTemplate
            .replaceCommitTimestamp(releaseCommit)
            .replaceBuildTimestamp(timestamp)

        if (!REGEX_PRE_RELEASE.toRegex().matches(message)) {
            throw IllegalArgumentException("Pre-release template contains invalid characters!")
        }

        return message
    }

    private fun String.replaceCommitTimestamp(commit: RevCommit): String {
        return replace(COMMIT_TIMESTAMP, commit.commitTime.toString())
    }

    private fun String.replaceBuildTimestamp(timestamp: OffsetDateTime): String {
        val formattedTimestamp = timestamp.toEpochSecond().toString()
        return replace(BUILD_TIMESTAMP, formattedTimestamp)
    }

    companion object {
        const val REGEX_PRE_RELEASE = "^(?!-)(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*\$"
        const val COMMIT_TIMESTAMP = "{COMMIT_TIMESTAMP}"
        const val BUILD_TIMESTAMP = "{BUILD_TIMESTAMP}"
    }
}
