import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

val javaVersion = JavaLanguageVersion.of("17")

java {
    toolchain {
        languageVersion.set(javaVersion)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = javaVersion.toString()
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}