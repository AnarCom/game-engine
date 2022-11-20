
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
//    // This dependency is exported to consumers, that is to say found on their compile classpath.
//    api("org.apache.commons:commons-math3:3.6.1")
}
