import gradle.kotlin.dsl.accessors._2b712f8a17e9c189211b18b2eb58b691.test

plugins {
    id("faktorybot-kotlin")
    id("com.palantir.git-version")
}

group = "io.github.raphiz"

version = run {
    val gitVersion: groovy.lang.Closure<String> by extra
    val version = gitVersion()
    if (version.startsWith("v")) version.substring(1)
    else version
}