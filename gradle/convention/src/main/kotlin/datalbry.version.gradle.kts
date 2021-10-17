import org.jetbrains.kotlin.util.removeSuffixIfPresent
import org.jetbrains.kotlin.util.suffixIfNot
import java.util.*

val version: String by project

tasks.register<WriteProperties>("writeVersion") {
    val propertiesFile = project.rootProject.file("gradle.properties")
    this.outputFile = propertiesFile
    val properties = Properties()
    properties.load(propertiesFile.inputStream())
    properties.forEach { this.property(it.key as String, it.value) }
    this.property("version", version)
}
