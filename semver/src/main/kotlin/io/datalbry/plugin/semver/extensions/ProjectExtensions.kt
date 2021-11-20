package io.datalbry.plugin.semver.extensions

import org.gradle.api.Project

inline fun <reified Type> Project.propertyOrNull(key: String): Type? {
    return if (project.properties.containsKey(key)) property(key) as Type else null
}

inline fun <reified Type> Project.propertyOrDefault(key: String, default: Type): Type {
    return propertyOrNull(key) ?: default
}
