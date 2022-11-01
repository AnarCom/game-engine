import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("no.tornado:tornadofx:1.7.20")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"//JavaVersion.VERSION_11.toString()
}

application {
    mainClass.set("ru.nsu.engine.Application")
}

javafx {
    version = "11.0.2"
    modules = listOf(
        "javafx.controls",
        "javafx.fxml",
        "javafx.base",
        "javafx.fxml",
        "javafx.media",
        "javafx.swing",
        "javafx.web",
        "javafx.graphics"
    )
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.nsu.engine.ApplicationKT"
    }
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
