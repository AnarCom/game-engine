import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
    maven{
        url=uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("no.tornado:tornadofx:1.7.20")
    implementation ("no.tornado:tornadofx:2.0.0-SNAPSHOT")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")

    implementation(project(":common"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"//JavaVersion.VERSION_11.toString()
}

application {
    mainClass.set("ru.nsu.editor.ApplicationKT")
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
        attributes["Main-Class"] = "ru.nsu.editor.ApplicationKT"
    }
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
