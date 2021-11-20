package io.datalbry.plugin.semver.version

import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.time.format.DateTimeFormatter
import org.eclipse.jgit.revwalk.RevCommit

/**
 * [PreReleaseSuffixResolver] resolves the snapshot suffix
 */
class PreReleaseSuffixResolver {

    /**
     * Resolves the suffix string by the pre release template
     *
     * @param preReleaseTemplate to format accordingly
     * @param releaseCommit to derive information from
     * @param timestamp of the build
     *
     * @return the formatted suffix
     */
    fun resolve(preReleaseTemplate: String, releaseCommit: RevCommit, timestamp: OffsetDateTime = now()): String {
        return preReleaseTemplate
            .replaceCommitTimestamp(releaseCommit)
            .replaceIsoDateTime(timestamp)
    }

    private fun String.replaceCommitTimestamp(commit: RevCommit): String {
        return replace(COMMIT_TIMESTAMP, commit.commitTime.toString())
    }

    private fun String.replaceIsoDateTime(timestamp: OffsetDateTime): String {
        val formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        return replace(ISO_DATE_TIME, formattedTimestamp)
    }

    companion object {
        const val COMMIT_TIMESTAMP = "{COMMIT_TIMESTAMP}"
        const val ISO_DATE_TIME = "{ISO_OFFSET_DATE_TIME}"
    }
}
