enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "semver-plugin"

includeBuild("gradle/convention")

include(
    "semver",
    "semver-github"
)

pluginManagement {
    plugins {
        val kotlinVersion = "1.5.30"

        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.spring") version kotlinVersion apply false
    }
}
