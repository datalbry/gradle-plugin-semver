package io.datalbry.plugin.semver

import io.datalbry.plugin.semver.task.PrintVersionTask
import io.datalbry.plugin.semver.templates.ReleaseTemplate
import io.datalbry.plugin.semver.task.VersionTagTask
import io.datalbry.plugin.semver.task.VersionUpdateTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.reflect.KClass

/**
 * The [SemVerPlugin] provides a convenient way to derive Semantic Versions by
 * the git history.
 *
 * @see io.datalbry.plugin.semver.task.VersionUpdateTask for information about the actual version update process
 *
 * @author timo gruen - 2021-10-12
 */
@Suppress("unused")
open class SemVerPlugin: Plugin<Project> {
    
    override fun apply(project: Project) {
        setupExtension(project)
        setupTasks(project)
    }

    open fun getExtensionClass(): KClass<out SemVerExtension> = SemVerExtension::class

    private fun setupExtension(project: Project) {
        project.extensions.create(EXTENSION_NAME, getExtensionClass().java)
    }

    private fun setupTasks(project: Project) {
        project.tasks.register(TAG_TASK_NAME, VersionTagTask::class.java)
        project.tasks.register(PRINT_VERSION_TASK_NAME, PrintVersionTask::class.java)
        project.tasks.register(updateTaskName(FINAL_RELEASE), VersionUpdateTask::class.java, false, "")
        project.afterEvaluate {
            project.extensions.getByType(getExtensionClass().java)
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
