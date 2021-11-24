package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.extensions.propertyOrDefault
import org.gradle.api.Project
import java.io.File
import javax.inject.Inject

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
        "semver.preReleaseTemplate",
        "dev.{COMMIT_TIMESTAMP}"
    )
}
