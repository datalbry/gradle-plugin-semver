package io.datalbry.plugin.semver.git

import io.datalbry.plugin.semver.extensions.isFullRelease
import io.datalbry.plugin.semver.version.VersionReader
import io.datalbry.plugin.semver.version.model.SemanticVersion
import java.io.File
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.revwalk.RevCommit
import org.jetbrains.kotlin.util.prefixIfNot


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
    fun getLatestFullVersion(): SemanticGitTag? {
        val tags = git.tagList().call()
        return git.log().call()
            .asSequence()
            .map { it to getTagOrNull(tags, it) }
            .filter { (_, tag) -> tag != null }
            .map { (commit, tag) -> commit to tag!!.getVersionString() }
            .map { (commit, tag) -> SemanticGitTag(commit, versionReader.readVersion(tag)) }
            .filter { it.version.isFullRelease() }
            .firstOrNull()
    }

    /**
     * Gets a list of commits
     *
     * @return [List] of [RevCommit]s
     */
    fun getCommits(): List<RevCommit> {
        return git.log().call().toList()
    }

    /**
     * Gets a list of commits
     *
     * @param from first commit, not to include in the list
     * @param until last commit which should be included
     *
     * @return [List] of [RevCommit]s
     */
    fun getCommits(from: RevCommit, until: RevCommit = getHead()): List<RevCommit> {
        return git.log().addRange(from, until).call().toList()
    }

    /**
     * Creates a Tag on the current Head
     *
     * @param version to tag
     */
    fun tagVersion(version: String, message: String = "") {
        git.tag().setName(version.prefixIfNot("v")).setMessage(message).call()
    }

    fun getHead(): RevCommit {
        return git.log().setMaxCount(1).call().first()
    }

    private fun getTagOrNull(tags: MutableList<Ref>, it: RevCommit) = tags.firstOrNull { a ->
        git.repository.refDatabase.peel(a).peeledObjectId?.name == it.id.name
    }
}

private fun Ref.getVersionString() = name.substringAfterLast("/").removePrefix("v")
