package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.task.PrintVersionTask
import io.datalbry.plugin.semver.task.VersionTagTask
import io.datalbry.plugin.semver.task.VersionUpdateTask
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * The [SemanticVersionPlugin] provides a convenient way to derive Semantic Versions by
 * the git history.
 *
 * @see io.datalbry.plugin.semver.task.VersionUpdateTask for information about the actual version update process
 *
 * @author timo gruen - 2021-10-12
 */
@Suppress("unused")
class SemanticVersionPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, SemanticVersionExtension::class.java)
        project.tasks.register(UPDATE_TASK_NAME, VersionUpdateTask::class.java)
        project.tasks.register(TAG_TASK_NAME, VersionTagTask::class.java)
        project.tasks.register(PRINT_VERSION_TASK_NAME, PrintVersionTask::class.java)
    }

    companion object {
        const val UPDATE_TASK_NAME = "updateVersion"
        const val TAG_TASK_NAME = "tag"
        const val PRINT_VERSION_TASK_NAME = "printVersion"
        const val TASK_GROUP_NAME = "semantic version"
        const val EXTENSION_NAME = "semanticVersion"
    }
}
