package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemanticVersionExtension
import io.datalbry.plugin.semver.SemanticVersionPlugin
import io.datalbry.plugin.semver.git.GitGraph
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * [TagVersionTask] tags the current head with the version passed either into the extension,
 * or if not set, falling back to the standard gradle version property
 *
 * @author timo gruen - 2021-10-17
 */
open class TagVersionTask: DefaultTask() {

    init {
        group = SemanticVersionPlugin.TASK_GROUP_NAME
    }

    @TaskAction
    fun publish() {
        val extension = project.extensions.getByType(SemanticVersionExtension::class.java)
        val rootDir = project.rootDir.absoluteFile

        val gitGraph = GitGraph(rootDir)
        gitGraph.tagVersion(extension.version)
    }

}
