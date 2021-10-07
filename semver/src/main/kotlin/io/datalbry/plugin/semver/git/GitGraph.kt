package io.datalbry.plugin.semver.git

import io.datalbry.plugin.semver.extensions.isFullRelease
import io.datalbry.plugin.semver.version.VersionReader
import io.datalbry.plugin.semver.version.model.SemanticVersion
import java.io.File
import org.gradle.api.Project
import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.eclipse.jgit.lib.Ref
import org.gradle.internal.impldep.org.eclipse.jgit.revwalk.RevCommit

/**
 * [GitGraph] is convenience class to access specific Git information from a directory
 *
 * @param gitDirectory to check the Git graph of
 *
 * @author timo gruen - 2021-10-05
 */
class GitGraph(gitDirectory: File) {

    private val git = Git.open(gitDirectory)
    private val versionReader = VersionReader()

    /**
     * Gets the latest full [SemanticVersion] of the current Git branch
     *
     * @return the last [SemanticVersion] of a full-version, if none present null
     */
    fun getLatestFullVersion(): SemanticVersion? {
        val tags = git.tagList().call()
        return git.log().call()
            .asSequence()
            .map { getTagOrNull(tags, it) }
            .filterNotNull()
            .map { it.name.substringAfterLast("/") }
            .map { it.removePrefix("v") }
            .map { versionReader.readVersion(it) }
            .filter { it.isFullRelease() }
            .firstOrNull()
    }

    private fun getTagOrNull(tags: MutableList<Ref>, it: RevCommit) = tags.firstOrNull { a ->
        git.repository.refDatabase.peel(a).peeledObjectId?.name == it.id.name
    }
}
