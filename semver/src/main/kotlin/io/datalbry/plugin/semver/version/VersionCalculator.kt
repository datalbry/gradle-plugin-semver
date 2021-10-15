package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.version.model.SemanticVersion

/**
 * [VersionCalculator] provides functionality to calculate a new version by the git commits
 * and the last version information
 *
 * @author timo gruen - 2021-10-07
 */
class VersionCalculator {

    /**
     * Calculates the next [SemanticVersion] by the given [commits]
     *
     * @param commits until the last release
     * @param lastVersion which has been released
     *
     * @return upcoming [SemanticVersion]
     */
    fun calculateNextVersion(commits: List<String>, lastVersion: SemanticVersion?): SemanticVersion {
        lastVersion ?: return SemanticVersion(0, 1,0)

        val anyBreakingChange = commits.any { it.hasBreakingChange() }
        if (anyBreakingChange) return lastVersion.copy(major = lastVersion.major + 1, minor = 0, patch = 0)

        val anyFeatureAdd = commits.any { it.hasFeatureChange() }
        if (anyFeatureAdd) return lastVersion.copy(minor = lastVersion.minor + 1, patch = 0)

        val anyBugFix = commits.any { it.hasBugFix() }
        if (anyBugFix) return lastVersion.copy(patch = lastVersion.patch + 1)

        return lastVersion
    }

    private fun String.hasBreakingChange(): Boolean {
        val matchResult = Regex(REGEX_COMMIT_MESSAGE).matchEntire(this)
        return matchResult?.groupValues?.getOrNull(4)?.let { it == "!"} ?: false || this.contains("BREAKING CHANGE")
    }

    private fun String.hasFeatureChange(): Boolean {
        val matchResult = Regex(REGEX_COMMIT_MESSAGE).matchEntire(this)
        return matchResult?.groupValues?.getOrNull(1)?.let { it == "feat" } ?: false
    }

    private fun String.hasBugFix(): Boolean {
        val matchResult = Regex(REGEX_COMMIT_MESSAGE).matchEntire(this)
        return matchResult?.groupValues?.getOrNull(1)?.let { it == "fix" } ?: false
    }

    companion object {
        const val REGEX_COMMIT_MESSAGE = """(\w{0,15})(\(?(.{0,40})\))?(!?):(.\S.*)"""
    }

}
