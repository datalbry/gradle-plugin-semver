package io.datalbry.plugin.semver.extensions

import io.datalbry.plugin.semver.SemVerExtension
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatterConfig

/**
 * Derives the [MarkdownReleaseNotesFormatterConfig] from the given [SemVerExtension]
 *
 * @receiver [SemVerExtension] holding any plugin configurations
 *
 * @return [MarkdownReleaseNotesFormatterConfig] for the formatter
 */
fun SemVerExtension.getMarkdownReleaseNotesFormatterConfig() = MarkdownReleaseNotesFormatterConfig(
    typeAlias = typeAlias
)
