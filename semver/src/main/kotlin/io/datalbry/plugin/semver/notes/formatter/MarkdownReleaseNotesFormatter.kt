package io.datalbry.plugin.semver.notes.formatter

import io.datalbry.plugin.semver.notes.ReleaseNote
import io.datalbry.plugin.semver.notes.ReleaseNotes
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter.Companion.HEADING_LEVEL_3
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter.Companion.HEADING_LEVEL_4
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter.Companion.LIST_PREFIX
import org.jetbrains.kotlin.util.prefixIfNot
import kotlin.collections.Map.Entry

/**
 * [MarkdownReleaseNotesFormatter] is a [ReleaseNotes] to markdown formatter
 *
 * @param config to alter the behavior of the [MarkdownReleaseNotesFormatter] with
 *
 * @author timo gruen - 2021-10-15
 */
class MarkdownReleaseNotesFormatter(
    private val config: MarkdownReleaseNotesFormatterConfig
) : ReleaseNotesFormatter {

    override fun format(releaseNotes: ReleaseNotes): String {
        return releaseNotes.messages
            .groupBy { it.type }
            .toSortedMap()
            .mapKeys { (key, _) -> config.typeAlias.getOrDefault(key, key) }
            .map { it.toMarkdown() }
            .joinToString("\n")
    }

    companion object {
        internal const val HEADING_LEVEL_3 = "###"
        internal const val HEADING_LEVEL_4 = "####"
        internal const val LIST_PREFIX = " - "
    }
}

private fun Entry<String, List<ReleaseNote>>.toMarkdown(): String {
    val builder = StringBuilder()
    builder.appendLine(this.key.getTitle())
    value.groupBy { it.context }
        .onEach {
            if (it.key.isNotBlank()) builder.appendLine("$HEADING_LEVEL_4 ${it.key.capitalize()}")
            it.value
                .map { commit -> commit.message }
                .map { message -> message.prefixIfNot(LIST_PREFIX) }
                .onEach(builder::appendLine)
        }
    return builder.toString()
}

private fun String.getTitle() = when {
    this.isEmpty() -> "$HEADING_LEVEL_3 Mentionable"
    else -> "$HEADING_LEVEL_3 ${capitalize()}"
}
