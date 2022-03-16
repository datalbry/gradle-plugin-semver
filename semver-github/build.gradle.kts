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
            id = "io.datalbry.plugin.semver"
            implementationClass = "io.datalbry.plugin.semver.SemanticVersionPlugin"
            displayName = "Gradle Semantic Version Plugin"
            description = "Simple plugin to update the gradle version property using the git history"

        }
    }

}

publishing {
    repositories {
        mavenLocal()
    }
}
