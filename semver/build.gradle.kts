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
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.stdlib)
    implementation(libs.org.eclipse.jgit)

    testImplementation(gradleTestKit())
    testImplementation(libs.junit.jupiter.api)

    testRuntime(libs.junit.jupiter.core)
    testRuntime(libs.junit.jupiter.engine)
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
