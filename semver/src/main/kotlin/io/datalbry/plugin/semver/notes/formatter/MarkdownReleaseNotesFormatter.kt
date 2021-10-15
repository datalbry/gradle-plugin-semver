package io.datalbry.plugin.semver.notes.formatter

import io.datalbry.plugin.semver.notes.ReleaseNote
import io.datalbry.plugin.semver.notes.ReleaseNotes
import org.jetbrains.kotlin.util.prefixIfNot
import kotlin.collections.Map.Entry

/**
 * [MarkdownReleaseNotesFormatter] is a [ReleaseNotes] to markdown formatter
 *
 * @author timo gruen - 2021-10-15
 */
object MarkdownReleaseNotesFormatter : ReleaseNotesFormatter {

    override fun format(releaseNotes: ReleaseNotes): String {
        val types = releaseNotes.messages.groupBy { it.type }
        return types.map { it.toMarkdown() }.joinToString("\n")
    }
}

private fun Entry<String, List<ReleaseNote>>.toMarkdown(): String {
    val builder = StringBuilder()
    builder.appendLine("### ${this.key.capitalize()}")
    value.groupBy { it.context }
        .onEach {
            if (key.isNotBlank()) builder.appendLine("#### ${it.key.capitalize()}")
            it.value
                .map { commit -> commit.message }
                .map { message -> message.prefixIfNot("- ") }
                .onEach(builder::appendLine)
        }
    return builder.toString()
}
