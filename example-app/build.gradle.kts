plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

group = "io.github.raphiz"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":faktory-bot-annotations"))
    ksp(project(":faktory-bot-processor"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}