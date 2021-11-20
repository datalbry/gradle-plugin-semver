package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.extensions.propertyOrDefault
import io.datalbry.plugin.semver.extensions.propertyOrNull
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project

abstract class SemanticVersionExtension @Inject constructor(project: Project) {

    var propertiesFile: File = File(
        project.propertyOrDefault(
            "semver.propertiesFile",
            project.rootProject.file("gradle.properties").absolutePath
        )
    )
    var version: String = project.propertyOrDefault(
        "semver.version",
        project.version.toString()
    )

    var isPreRelease: Boolean = project.propertyOrDefault(
        "semver.isPreRelease",
        false
    )

    var preReleaseTemplate: String = project.propertyOrDefault(
        "semver.preReleaseFormat",
        "-dev.{COMMIT_TIMESTAMP}"
    )
}
