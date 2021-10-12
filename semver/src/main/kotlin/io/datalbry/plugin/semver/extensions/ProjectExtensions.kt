package io.datalbry.plugin.semver.extensions

import java.io.File
import org.gradle.api.Project

inline fun <reified Type> Project.propertyOrNull(key: String): Type? {
    return if (project.properties.containsKey(key)) property(key) as Type else null
}

inline fun <reified Type> Project.propertyOrDefault(key: String, default: Type): Type {
    return propertyOrNull(key) ?: default
}

fun Project.resolveFile(path: String): File {
    return if (path.startsWith(".")) {
        File(project.projectDir.absolutePath, path)
    } else File(path)
}
