package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.task.UpdateVersionTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The [SemanticVersionPlugin] provides a convenient way to derive Semantic Versions by
 * the git history.
 *
 * @see io.datalbry.plugin.semver.task.UpdateVersionTask for information about the actual version update process
 *
 * @author timo gruen - 2021-10-12
 */
@Suppress("unused")
class SemanticVersionPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, SemanticVersionExtension::class.java)
        project.tasks.register(TASK_NAME, UpdateVersionTask::class.java)
    }

    companion object {
        const val TASK_NAME = "updateVersion"
        const val EXTENSION_NAME = "semver"
    }
}
