package io.datalbry.plugin.semver.version

import io.datalbry.plugin.semver.version.model.SemanticVersion
import java.io.File
import java.io.FileWriter
import java.util.Properties

/**
 * [VersionWriter] supports writing versions to a properties file
 *
 * @author timo gruen - 2021-10-05
 */
class VersionWriter {

    /**
     * Writes the [semanticVersion] to the passed file
     *
     * @param propertiesFile to write the version to
     * @param semanticVersion to write
     */
    fun writeVersion(propertiesFile: File, semanticVersion: SemanticVersion) {
        val properties = Properties()
        properties.load(propertiesFile.inputStream())
        properties["version"] = "${semanticVersion.major}.${semanticVersion.minor}.${semanticVersion.patch}"

        val writer = FileWriter(propertiesFile)
        properties.store(writer, "")
    }

    companion object {
        const val GRADLE_PROPERTIES_FILE_NAME = "gradle.properties"
    }

}
