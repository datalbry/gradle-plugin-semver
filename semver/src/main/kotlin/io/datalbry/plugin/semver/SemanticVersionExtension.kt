package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.extensions.propertyOrDefault
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

}
