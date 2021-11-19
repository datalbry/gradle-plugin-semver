package io.datalbry.plugin.semver.notes

/**
 * The [ReleaseNotesExtractor] extracts release notes from conventional commit messages
 *
 * @author timo gruen - 2021-10-14
 */
class ReleaseNotesExtractor {

    /**
     * Extracts release notes from messages
     *
     * @param messages to extract the release notes from
     *
     * @return [ReleaseNotes] containing all messages
     */
    fun extractReleaseNotes(messages: Collection<String>): ReleaseNotes {
        val notes = messages.map { message -> extractReleaseNote(message) }.toSet()
        return ReleaseNotes(notes)
    }

    private fun extractReleaseNote(message: String): ReleaseNote {
        val matchResult = Regex(REGEX_COMMIT_MESSAGE).matchEntire(message)
        val type = matchResult?.groupValues?.getOrNull(1) ?: ""
        val context = matchResult?.groupValues?.getOrNull(3) ?: ""
        val body = matchResult?.groupValues?.getOrNull(5) ?: ""

        return ReleaseNote(type = type, context = context, message = body)
    }

    companion object {
        const val REGEX_COMMIT_MESSAGE = """(\w{0,15})(\(?(.{0,40})\))?(!?):(.\S.*)"""
    }
}
