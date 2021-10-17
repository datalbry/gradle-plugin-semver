package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemanticVersionExtension
import io.datalbry.plugin.semver.SemanticVersionPlugin
import io.datalbry.plugin.semver.git.GitGraph
import io.datalbry.plugin.semver.notes.ReleaseNotesExtractor
import io.datalbry.plugin.semver.notes.formatter.MarkdownReleaseNotesFormatter
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
        val commits = lastVersion?.let { gitGraph.getCommits(from = it.commit) } ?: gitGraph.getCommits()
        val releaseNotes = releaseNotesExtractor.extractReleaseNotes(commits.map { it.fullMessage })
        val releaseNotesString = MarkdownReleaseNotesFormatter.format(releaseNotes)
        gitGraph.tagVersion(extension.version, releaseNotesString)
    }

}
