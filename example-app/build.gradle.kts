plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

group = "io.github.raphiz"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":faktory-bot-annotations"))
    ksp(project(":faktory-bot-processor"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.test {
    useJUnitPlatform()
}