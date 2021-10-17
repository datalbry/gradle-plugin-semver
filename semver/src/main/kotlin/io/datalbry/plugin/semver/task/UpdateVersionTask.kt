package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemanticVersionExtension
import io.datalbry.plugin.semver.SemanticVersionPlugin.Companion.TASK_GROUP_NAME
import io.datalbry.plugin.semver.git.GitGraph
import io.datalbry.plugin.semver.version.VersionCalculator
import io.datalbry.plugin.semver.version.VersionWriter
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * [UpdateVersionTask] updates the version in the `gradle.properties` file
 * by calculating the next version using the Git commit messages.
 *
 * The task is relying on conventional commits to derive the next version by commit messages.
 * Having a history like:
 * - fix(web): clicking on button having side effects
 * - chore(ci): add version calculation
 * - feat(core): Add new attribute to Versions API
 *
 * will result into having an upgrade on the minor version, as there is no breaking change indicated,
 * and the `feat` message is the one with the most invasive change.
 *
 * _NOTE:_
 * Checkout https://www.conventionalcommits.org/en/v1.0.0/ for further information about conventional commits.
 *
 * @author timo gruen - 2021-10-10
 */
open class UpdateVersionTask : DefaultTask() {

    private val versionCalculator = VersionCalculator()
    private val versionWriter = VersionWriter()

    init {
        group = TASK_GROUP_NAME
    }

    @TaskAction
    fun publish() {
        val extension = project.extensions.getByType(SemanticVersionExtension::class.java)
        val rootDir = project.rootDir.absoluteFile

        val gitGraph = GitGraph(rootDir)
        val lastVersion = gitGraph.getLatestFullVersion()
        val commitsSinceLastVersion = lastVersion
            ?.let { gitGraph.getCommits(it.commit) }
            ?.map { it.name }
            ?: emptyList()
        val nextVersion = versionCalculator.calculateNextVersion(commitsSinceLastVersion, lastVersion?.version)

        val propertiesFile = extension.propertiesFile
        versionWriter.writeVersion(propertiesFile, nextVersion)
    }

}
