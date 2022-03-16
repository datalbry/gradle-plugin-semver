package io.datalbry.plugin.semver.github.task

import io.datalbry.plugin.semver.SemVerPlugin
import io.datalbry.plugin.semver.github.GithubSemVerExtension
import io.datalbry.plugin.semver.github.client.DefaultGithubClient
import io.datalbry.plugin.semver.notes.ReleaseNotes
import io.datalbry.plugin.semver.version.VersionReader
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GithubReleaseTask: DefaultTask() {

    init {
        group = SemVerPlugin.TASK_GROUP_NAME
    }

    private val versionReader = VersionReader()

    @TaskAction
    fun createRelease() {
        val extension = project.extensions.getByType(GithubSemVerExtension::class.java)
        val version = versionReader.readVersion(extension.version)
        val notes = ReleaseNotes(emptySet())
        DefaultGithubClient(extension.repository, extension.token).createRelease(version, notes)
    }
}
