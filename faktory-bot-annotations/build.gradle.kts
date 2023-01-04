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
