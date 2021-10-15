package io.datalbry.plugin.semver.notes

data class ReleaseNotes(
    val messages: Set<ReleaseNote>
)

data class ReleaseNote(
    val type: String,
    val context: String,
    val message: String
)
