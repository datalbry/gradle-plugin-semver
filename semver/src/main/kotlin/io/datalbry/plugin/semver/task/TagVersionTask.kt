package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemanticVersionExtension
import io.datalbry.plugin.semver.SemanticVersionPlugin
import io.datalbry.plugin.semver.git.GitGraph
import io.datalbry.plugin.semver.git.SemanticGitTag
import io.datalbry.plugin.semver.notes.ReleaseNotesExtractor
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter
import org.eclipse.jgit.revwalk.RevCommit
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * [TagVersionTask] tags the current head with the version passed either into the extension,
 * or if not set, falling back to the standard gradle version property
 *
 * @author timo gruen - 2021-10-17
 */
open class TagVersionTask: DefaultTask() {

    private val releaseNotesExtractor = ReleaseNotesExtractor()

    init {
        group = SemanticVersionPlugin.TASK_GROUP_NAME
    }

    @TaskAction
    fun publish() {
        val extension = project.extensions.getByType(SemanticVersionExtension::class.java)
        val rootDir = project.rootDir.absoluteFile
        val gitGraph = GitGraph(rootDir)

        val lastVersion = gitGraph.getLatestFullVersion()
        logLastVersion(lastVersion)

        val commits = lastVersion?.let { gitGraph.getCommits(from = it.commit) } ?: gitGraph.getCommits()
        logCommits(commits)

        val releaseNotes = releaseNotesExtractor.extractReleaseNotes(commits.map { it.fullMessage })
        val releaseNotesString = MarkdownReleaseNotesFormatter.format(releaseNotes)
        gitGraph.tagVersion(extension.version, releaseNotesString)
    }

    private fun logLastVersion(lastVersion: SemanticGitTag?) {
        if (lastVersion == null) {
            project.logger.info("Last version not found")
        } else project.logger.info("Last version found is: ${lastVersion.version}")
    }

    private fun logCommits(commits: List<RevCommit>) {
        if (commits.isEmpty()) {
            project.logger.info("No commits have been found")
        } else {
            project.logger.info("Found ${commits.size} commits since last release")
            commits.onEach {
                project.logger.trace(it.shortMessage)
            }
        }
    }
}
