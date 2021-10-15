package io.datalbry.plugin.semver.notes.formatter

import io.datalbry.plugin.semver.notes.ReleaseNote
import io.datalbry.plugin.semver.notes.ReleaseNotes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MarkdownReleaseNotesFormatterTest {

    @Test
    fun test() {
        val releaseNotes = ReleaseNotes(setOf(
            ReleaseNote("test", "robin", "Test new Bow"),
            ReleaseNote("fix", "batman", "Add new tires to Batmobile"),
            ReleaseNote("test", "batman", "Test Batmobile"),
        ))
        val expectedBody = """
          ### Test
          #### Robin
          - Test new Bow
          #### Batman
          - Test Batmobile

          ### Fix
          #### Batman
          - Add new tires to Batmobile
        """.trimIndent()

        val formattedReleaseNotes = MarkdownReleaseNotesFormatter.format(releaseNotes)
        assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
    }

}
