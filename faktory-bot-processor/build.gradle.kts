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
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    implementation(project(":faktory-bot-annotations"))
    
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.10-1.0.6")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.4.9")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}