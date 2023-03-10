plugins {
    `faktorybot-module`
    `faktorybot-releasable-artifact`
}

description = "Generate factories to cleanly create and maintain test data"

dependencies {
    implementation("com.squareup:kotlinpoet:1.12.0")
    implementation("com.squareup:kotlinpoet-ksp:1.12.0")
    implementation(project(":faktory-bot-annotations"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.10-1.0.9")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation(kotlin("reflect"))
}
