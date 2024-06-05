plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies{
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.9.24")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:3.1.0")
}