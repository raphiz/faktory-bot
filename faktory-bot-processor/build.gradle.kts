plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
}

group = "io.github.raphiz"

version = run {
    val gitVersion: groovy.lang.Closure<String> by extra
    val version = gitVersion()
    if (version.startsWith("v")) version.substring(1)
    else version
}

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