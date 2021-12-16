package io.datalbry.plugin.semver.templates

/**
 * [ReleaseTemplate] contains information of a supported version type
 *
 * The [ReleaseTemplate] is useful to add support for multiple release versions, such as beta, alpha or rc and SNAPSHOT.
 * Checkout the documentation for more details.
 *
 * @param name of the version type
 * @param template of the version
 */
data class ReleaseTemplate(
    val name: String,
    val template: String
)
