plugins {
    id("datalbry.version")
    idea
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

group = "io.datalbry.gradle"

val values = tasks.create<Copy>("prepareDocs") {
    from("templates/docs")
    into("docs")
    expand(project.properties)
}
