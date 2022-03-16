import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("datalbry.kotlin")
    id("datalbry.plugin")
    id("java-gradle-plugin")
    id("maven-publish")
}

version = rootProject.version

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    implementation(project(":semver"))
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kohsuke.github)

    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter.api)

    testRuntimeOnly(libs.junit.jupiter.core)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

gradlePlugin {
    plugins {
        create("plugin") {
            id = "io.datalbry.plugin.semver.github"
            implementationClass = "io.datalbry.plugin.semver.github.GithubSemVerPlugin"
            displayName = "Gradle Semantic Version Plugin for GitHub"
            description = "Simple plugin to update the gradle version property using the git history " +
                    "and create GitHub releases"
        }
    }

}

publishing {
    repositories {
        mavenLocal()
    }
}
