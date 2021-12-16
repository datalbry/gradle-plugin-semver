package io.datalbry.plugin.semver.extensions

import io.datalbry.plugin.semver.templates.ReleaseTemplate
import org.gradle.api.Project

inline fun <reified Type> Project.propertyOrNull(key: String): Type? {
    return if (project.properties.containsKey(key)) {
        val property = property(key).toString()
        when (Type::class) {
            Boolean::class -> property.toBoolean() as Type
            String::class -> property as Type
            Integer::class -> property.toInt() as Type
            Long::class -> property.toLong() as Type
            else -> throw IllegalArgumentException("Can't fetch Property[$key] with Type[${Type::class.simpleName}]")
        }
    }
    else null
}

inline fun <reified Type> Project.propertyOrDefault(key: String, default: Type): Type {
    return propertyOrNull(key) ?: default
}

fun Project.getPreReleaseTemplates(key: String): List<ReleaseTemplate> {
    return project.properties.entries
        .filter { it.key.startsWith(key) }
        .map { it.key to (propertyOrNull<String>(it.key) ?: throw IllegalArgumentException()) }
        .map { ReleaseTemplate(it.first, it.second) }
}
