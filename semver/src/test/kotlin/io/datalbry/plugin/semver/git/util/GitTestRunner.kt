package io.datalbry.plugin.semver.git.util

import io.datalbry.plugin.semver.git.GitGraph
import io.datalbry.plugin.semver.version.model.SemanticVersion
import java.io.File
import java.util.UUID
import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createTempDirectory

@OptIn(ExperimentalPathApi::class)
fun gitTest(vararg commits: Pair<String, SemanticVersion?>, run: (GitGraph) -> Unit) {
    val tmpDir = createTempDirectory(directory = null, prefix = null).toFile()
    try {
        val git = Git.init().setGitDir(tmpDir).call()
        val newFile = File(tmpDir, "change_me.txt")

        commits.forEach { (commitMessage, version) ->
            newFile.writeText(UUID.randomUUID().toString())
            git.add().addFilepattern(newFile.name).call()
            git.commit().setMessage(commitMessage).call()
            version?.let { git.tag().setName("v$it").call() }
        }
        val gitGraph = GitGraph(tmpDir)
        run(gitGraph)
    } finally {
        tmpDir.deleteRecursively()
    }
}
