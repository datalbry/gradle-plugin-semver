plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("com.gradle.publish:plugin-publish-plugin:0.15.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("io.github.gradle-nexus:publish-plugin:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.4.31")
    implementation("org.jetbrains.kotlin:kotlin-noarg:1.4.31")
}
