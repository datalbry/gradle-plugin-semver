package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.version.model.SemanticVersion
import kotlin.jvm.Throws

/**
 * [VersionReader] provides support to read a [SemanticVersion]
 *
 *  @author timo gruen - 2021-10-07
 */
class VersionReader {

    /**
     * Reads a [SemanticVersion] from a potential valid SemanticVersion
     *
     * @param version in String representation
     *
     * @throws IllegalArgumentException if the Version is not a valid v2 SemVer
     */
    @Throws(IllegalArgumentException::class)
    fun readVersion(version: String): SemanticVersion {
        val matchResult = Regex(VALIDATION_REGEX).matchEntire(version)
        matchResult ?: throw IllegalArgumentException("Version[$version] is not a valid v2 Semantic Version")
        return SemanticVersion(
            major = matchResult.groupValues.getOrNull(1)?.toInt() ?: 0,
            minor = matchResult.groupValues.getOrNull(2)?.toInt() ?: 0,
            patch = matchResult.groupValues.getOrNull(3)?.toInt() ?: 0,
            preRelease = matchResult.groupValues[4].ifEmpty { null },
            buildMetadata = matchResult.groupValues[5].ifEmpty { null }
        )
    }

    companion object {
        // The REGEX is provided by https://semver.org
        private const val VALIDATION_REGEX = """^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?${'$'}"""
    }

}
