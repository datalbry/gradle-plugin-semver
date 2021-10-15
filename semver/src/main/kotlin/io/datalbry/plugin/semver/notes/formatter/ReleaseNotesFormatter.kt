package io.datalbry.plugin.semver.notes.formatter

import io.datalbry.plugin.semver.notes.ReleaseNotes

/**
 * [ReleaseNotesFormatter] is a generic interface to map a given [ReleaseNotes] to a specific String format
 *
 * @see [MarkdownReleaseNotesFormatter] for Markdown formatting, required e.g. by GitHub
 */
sealed interface ReleaseNotesFormatter {

    /**
     * Formats [ReleaseNotes] to a string
     *
     * @param [releaseNotes] to format
     *
     * @return formatted [ReleaseNotes]
     */
    fun format(releaseNotes: ReleaseNotes): String

}
