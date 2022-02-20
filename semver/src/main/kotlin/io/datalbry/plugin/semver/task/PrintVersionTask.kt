package io.datalbry.plugin.semver.task

import io.datalbry.plugin.semver.SemVerPlugin.Companion.TASK_GROUP_NAME
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * The [PrintVersionTask] prints the version of the project to stdout.
 *
 * @author gerald nikolaus - 2021-11-24
 */
open class PrintVersionTask : DefaultTask() {

    init {
        group = TASK_GROUP_NAME
    }

    @TaskAction
    fun publish() {
        println(project.version)
    }
}
