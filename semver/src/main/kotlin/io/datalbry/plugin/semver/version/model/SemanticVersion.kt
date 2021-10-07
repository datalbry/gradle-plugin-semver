package io.datalbry.plugin.semver.version.model

/**
 * [SemanticVersion] stores version information of a valid Semantic Version
 *
 * Checkout [semver](https://semver.org) for further information
 *
 * @param major version
 * @param minor version
 * @param patch version
 * @param preRelease suffix
 * @param buildMetadata suffix
 *
 * Version should look the following: <MAJOR>.<MINOR>.<PATCH>-<PRE_RELEASE>+<BUILD_INFORMATION>
 *
 * @author timo gruen - 2021-10-05
 */
data class SemanticVersion(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val preRelease: String? = null,
    val buildMetadata: String? = null
) {

    override fun toString() = buildString {
        append("$major.$minor.$patch")
        if (preRelease != null) {
            append('-')
            append(preRelease)
        }
        if (buildMetadata != null) {
            append('+')
            append(buildMetadata)
        }
    }
}

