package io.datalbry.plugin.semver.github

import io.datalbry.plugin.semver.SemVerPlugin

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

}
