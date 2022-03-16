package io.datalbry.plugin.semver.github

import io.datalbry.plugin.semver.SemVerExtension
import io.datalbry.plugin.semver.extensions.propertyOrDefault
import javax.inject.Inject
import org.gradle.api.Project

abstract class GithubSemVerExtension @Inject constructor(project: Project): SemVerExtension(project) {

    var repository: String = project.propertyOrDefault("semver.repository", "")
    var token: String = project.propertyOrDefault("semver.token", "")

}
