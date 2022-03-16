package io.datalbry.plugin.semver.github

import io.datalbry.plugin.semver.SemVerPlugin
import io.datalbry.plugin.semver.github.task.GithubReleaseTask
import org.gradle.api.Project

/**
 * The [GithubSemVerPlugin] provides a convenient way to derive Semantic Versions by
 * the git history.
 *
 * @see io.datalbry.plugin.semver.task.VersionUpdateTask for information about the actual version update process
 *
 * @author timo gruen - 2021-10-12
 */
@Suppress("unused")
class GithubSemVerPlugin: SemVerPlugin() {

    override fun getExtensionClass() = GithubSemVerExtension::class
    override fun apply(project: Project) {
        super.apply(project)
        setupTasks(project)
    }

    private fun setupTasks(project: Project) {
        project.tasks.register(GITHUB_RELEASE_TASK_NAME, GithubReleaseTask::class.java)
    }

    companion object {
        const val GITHUB_RELEASE_TASK_NAME = "createGithubRelease"
    }
}
