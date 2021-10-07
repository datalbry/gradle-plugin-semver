package io.datalbry.plugin.semver.extensions

import io.datalbry.plugin.semver.version.model.SemanticVersion

fun SemanticVersion.isFullRelease(): Boolean {
    return this.preRelease == null
}
