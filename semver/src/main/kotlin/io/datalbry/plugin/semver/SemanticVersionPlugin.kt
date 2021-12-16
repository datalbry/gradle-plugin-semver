package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.task.PrintVersionTask
import io.datalbry.plugin.semver.templates.ReleaseTemplate
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
        project.tasks.register(TAG_TASK_NAME, VersionTagTask::class.java)
        project.tasks.register(PRINT_VERSION_TASK_NAME, PrintVersionTask::class.java)
        project.tasks.register(updateTaskName(FINAL_RELEASE), VersionUpdateTask::class.java, false, "")
        project.afterEvaluate {
            project.extensions.getByType(SemanticVersionExtension::class.java)
                .preReleaseTemplates
                .forEach { it.registerPreReleaseTask(project) }
        }
    }

    private fun ReleaseTemplate.registerPreReleaseTask(project: Project, ) {
        val taskName = updateTaskName(name)
        project.tasks.register(taskName, VersionUpdateTask::class.java, true, template)
    }

    private fun updateTaskName(releaseName: String) = "update${releaseName.capitalize()}Version"

    companion object {
        const val FINAL_RELEASE = "release"
        const val TAG_TASK_NAME = "tag"
        const val PRINT_VERSION_TASK_NAME = "printVersion"
        const val TASK_GROUP_NAME = "semver"
        const val EXTENSION_NAME = "semver"
    }
}
