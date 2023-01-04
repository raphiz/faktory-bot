plugins {
    kotlin("jvm")
    id("com.palantir.git-version")
}

group = "io.github.raphiz"
val gitVersion: groovy.lang.Closure<String> by extra
version = gitVersion()


repositories {
    mavenCentral()
}
