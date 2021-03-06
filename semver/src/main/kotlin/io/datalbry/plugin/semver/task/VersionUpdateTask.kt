package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemVerExtension
import io.datalbry.plugin.semver.SemVerPlugin.Companion.TASK_GROUP_NAME
import io.datalbry.plugin.semver.git.GitGraph
import io.datalbry.plugin.semver.git.SemanticGitTag
import io.datalbry.plugin.semver.version.PreReleaseTemplateResolver
import io.datalbry.plugin.semver.version.VersionCalculator
import io.datalbry.plugin.semver.version.VersionReader
import io.datalbry.plugin.semver.version.VersionWriter
import io.datalbry.plugin.semver.version.model.SemanticVersion
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * [VersionUpdateTask] updates the version in the `gradle.properties` file
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
open class VersionUpdateTask @Inject constructor(
    private val isPreRelease: Boolean,
    private val template: String
) : DefaultTask() {

    private val versionCalculator = VersionCalculator()
    private val preReleaseTemplateResolver = PreReleaseTemplateResolver()
    private val versionWriter = VersionWriter()
    private val versionReader = VersionReader()

    init {
        group = TASK_GROUP_NAME
    }

    @TaskAction
    fun publish() {
        val extension = project.extensions.getByType(SemVerExtension::class.java)
        val rootDir = project.rootDir.absoluteFile

        val gitGraph = GitGraph(rootDir)
        val lastVersion = gitGraph.getLatestFullVersion()
        logLastVersion(lastVersion)

        val commitsSinceLastVersion = lastVersion
            ?.let { gitGraph.getCommits(it.commit) }
            ?.map { it.shortMessage }
            ?: emptyList()
        logCommits(commitsSinceLastVersion)

        val preRelease = if (isPreRelease) {
            val lastCommit = gitGraph.getHead()
            preReleaseTemplateResolver.resolve(template, lastCommit)
        } else null
        val nextVersionCandidate = versionCalculator
            .calculateNextVersion(commitsSinceLastVersion, lastVersion?.version)

        val nextVersion = if (extension.baseline) {
            getHigherVersion(
                versionReader.readVersion(extension.version),
                nextVersionCandidate
            ).copy(preRelease = preRelease)
        } else nextVersionCandidate.copy(preRelease = preRelease)

        logVersion(nextVersion)
        val propertiesFile = extension.propertiesFile
        versionWriter.writeVersion(propertiesFile, nextVersion)
    }

    private fun logVersion(nextVersion: SemanticVersion) {
        project.logger.info("Next version is $nextVersion")
    }

    private fun logCommits(commitsSinceLastVersion: List<String>) {
        if (commitsSinceLastVersion.isEmpty()) {
            project.logger.info("No commits have been found")
        } else {
            project.logger.info("Found ${commitsSinceLastVersion.size} commits since last release")
            commitsSinceLastVersion.onEach {
                project.logger.trace(it)
            }
        }
    }

    private fun logLastVersion(lastVersion: SemanticGitTag?) {
        if (lastVersion == null) {
            project.logger.info("Last version not found")
        } else project.logger.info("Last version found is: ${lastVersion.version}")
    }
}

private fun getHigherVersion(first: SemanticVersion, second: SemanticVersion) = when {
    first.isHigher(second) -> first
    else -> second
}

private fun SemanticVersion.isHigher(than: SemanticVersion): Boolean {
    return ((major > than.major) ||
            (major == than.major && minor > than.minor) ||
            (major == than.major && minor == than.minor && patch > than.patch) ||
            (major == than.major && minor == than.minor && patch == than.patch
                    && isNaturalComparedHigher(preRelease, than.preRelease)))
}

private fun isNaturalComparedHigher(s1: String?, s2: String?): Boolean {
    return when {
        s1 == null && s2 != null -> true
        s1 == null && s2 == null -> false
        s1 != null && s2 == null -> false
        else -> naturalOrder<String>().compare(s1, s2) <= 1
    }
}
