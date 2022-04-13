package io.datalbry.plugin.semver.notes.formatter

/**
 * Configuration of the [MarkdownReleaseNotesFormatter]
 *
 * @param typeAlias to alias any given types with
 */
data class MarkdownReleaseNotesFormatterConfig(
    val typeAlias: Map<String, String> = emptyMap()
)
