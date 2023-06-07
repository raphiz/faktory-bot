plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies{
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:1.8.22")
    implementation("com.palantir.gradle.gitversion:gradle-git-version:3.0.0")
}