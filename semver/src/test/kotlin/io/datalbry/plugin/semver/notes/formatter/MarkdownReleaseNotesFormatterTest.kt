package io.datalbry.plugin.semver.notes.formatter

import io.datalbry.plugin.semver.notes.ReleaseNote
import io.datalbry.plugin.semver.notes.ReleaseNotes
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MarkdownReleaseNotesFormatterTest {

    @Test
    fun `context is grouped correct`() {
        val conf = MarkdownReleaseNotesFormatterConfig()
        val fixture = MarkdownReleaseNotesFormatter(conf)
        val releaseNotes = ReleaseNotes(setOf(
            ReleaseNote("test", "robin", "Test new Bow"),
            ReleaseNote("fix", "batman", "Fix broken tires of the Batmobile"),
            ReleaseNote("test", "batman", "Test Batmobile"),
        ))
        val expectedBody = """
          ### Fix
          #### Batman
           - Fix broken tires of the Batmobile
          
          ### Test
          #### Robin
           - Test new Bow
          #### Batman
           - Test Batmobile
        """.trimIndent()

        val formattedReleaseNotes = fixture.format(releaseNotes)
        assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
    }

    @Test
    fun `empty type is grouped into mentionable`() {
        val conf = MarkdownReleaseNotesFormatterConfig()
        val fixture = MarkdownReleaseNotesFormatter(conf)
        val releaseNotes = ReleaseNotes(setOf(
            ReleaseNote("", "", "Look for a new butler"),
            ReleaseNote("fix", "batman", "Fix broken tires of the Batmobile"),
            ReleaseNote("fix", "batman", "Fix breakes of the Batmobile"),
        ))
        val expectedBody = """
          ### Mentionable
           - Look for a new butler
          
          ### Fix
          #### Batman
           - Fix broken tires of the Batmobile
           - Fix breakes of the Batmobile
        """.trimIndent()

        val formattedReleaseNotes = fixture.format(releaseNotes)
        assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
    }

    @Test
    fun `empty context is grouped right next to the type`() {
        val conf = MarkdownReleaseNotesFormatterConfig()
        val fixture = MarkdownReleaseNotesFormatter(conf)
        val releaseNotes = ReleaseNotes(setOf(
            ReleaseNote("fix", "", "Remove guano in the Batcave"),
            ReleaseNote("fix", "batman", "Fix broken tires of the Batmobile"),
            ReleaseNote("fix", "batman", "Fix breakes of the Batmobile"),
        ))
        val expectedBody = """
          ### Fix
           - Remove guano in the Batcave
          #### Batman
           - Fix broken tires of the Batmobile
           - Fix breakes of the Batmobile
        """.trimIndent()

        val formattedReleaseNotes = fixture.format(releaseNotes)
        assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
    }

    @Test
    fun `alias types works just fine`() {
        val aliases = mapOf(
            "fix" to "Bugfix",
            "feat" to "Feature"
        )
        val conf = MarkdownReleaseNotesFormatterConfig(aliases)
        val fixture = MarkdownReleaseNotesFormatter(conf)
        val releaseNotes = ReleaseNotes(setOf(
            ReleaseNote("feat", "", "Add a Bow to the Batmobile"),
            ReleaseNote("fix", "", "Fix broken tires of the Batmobile"),
        ))

        val expectedBody = """
          ### Feature
           - Add a Bow to the Batmobile
          
          ### Bugfix
           - Fix broken tires of the Batmobile
        """.trimIndent()

        val formattedReleaseNotes = fixture.format(releaseNotes)
        assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
    }

    @Test
    fun `types sort remains stable`() {
        val aliases = mapOf(
            "fix" to "Bugfix",
            "feat" to "Feature"
        )
        val conf = MarkdownReleaseNotesFormatterConfig(aliases)
        val fixture = MarkdownReleaseNotesFormatter(conf)
        val notes = listOf(
            ReleaseNotes(setOf(
                ReleaseNote("feat", "", "Add a Bow to the Batmobile"),
                ReleaseNote("fix", "", "Fix broken tires of the Batmobile"),
            )),
            ReleaseNotes(setOf(
                ReleaseNote("fix", "", "Fix broken tires of the Batmobile"),
                ReleaseNote("feat", "", "Add a Bow to the Batmobile")
            ))
        )

        val expectedBody = """
          ### Feature
           - Add a Bow to the Batmobile
          
          ### Bugfix
           - Fix broken tires of the Batmobile
        """.trimIndent()

        notes.forEach { releaseNotes ->
            val formattedReleaseNotes = fixture.format(releaseNotes)
            assertTrue(formattedReleaseNotes.trim().contentEquals(expectedBody))
        }
    }
}
