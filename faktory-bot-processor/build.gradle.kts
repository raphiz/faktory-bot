plugins {
    `faktorybot-module`
    `faktorybot-releasable-artifact`
}

description = "Generate factories to cleanly create and maintain test data"

dependencies {
    implementation("com.squareup:kotlinpoet:1.15.0")
    implementation("com.squareup:kotlinpoet-ksp:1.14.2")
    implementation(project(":faktory-bot-annotations"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.14")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(kotlin("reflect"))
}
