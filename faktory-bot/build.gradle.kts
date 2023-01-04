plugins {
    kotlin("jvm") version "1.7.10"
    id("com.palantir.git-version") version "0.15.0"
}

group = "io.github.raphiz"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("com.squareup:kotlinpoet:1.12.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

tasks.test {
    useJUnitPlatform()
}