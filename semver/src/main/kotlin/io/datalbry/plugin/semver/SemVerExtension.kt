package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.extensions.propertyOrDefault
import io.datalbry.plugin.semver.templates.ReleaseTemplate
import java.io.File
import javax.inject.Inject
import org.gradle.api.Project

abstract class SemVerExtension @Inject constructor(project: Project) {

    var propertiesFile: File = File(
        project.propertyOrDefault(
            "semver.propertiesFile",
            project.rootProject.file("gradle.properties").absolutePath
        )
    )

    var baseline: Boolean = project.propertyOrDefault(
        "semver.baseline",
        true
    )

    var typeAlias: MutableMap<String, String> = mutableMapOf(
        "fix" to "Bugfix",
        "feat" to "Feature",
        "BREAKING CHANGE" to "Breaking Change"
    )

    fun alias(type: String, alias: String) {
        typeAlias[type] = alias
    }

    var version: String = project.propertyOrDefault(
        "semver.version",
        project.version.toString()
    )

    var preReleaseTemplates: MutableList<ReleaseTemplate> = mutableListOf()

    fun version(name: String, template: String) {
        val releaseTemplate = ReleaseTemplate(name, template)
        preReleaseTemplates.add(releaseTemplate)
    }
}
