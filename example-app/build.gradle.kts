plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "1.9.24-1.0.20"
}

group = "io.github.raphiz"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":faktory-bot-annotations"))
    ksp(project(":faktory-bot-processor"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.26.0")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}