plugins {
    id("com.gradle.plugin-publish")
}

pluginBundle {
    website = "https://datalbry.io"
    vcsUrl = "https://github.com/datalbry/gradle-semver-plugin"
    tags = listOf("semantic", "version", "semver", "ci", "release", "release-management", "versioning")
    mavenCoordinates {
        val group = project.group.toString()
        this.groupId = group
        this.artifactId = "${groupId.split(".").last()}.${project.name}"
        this.version = project.version.toString()
        this.group = group
    }
}
