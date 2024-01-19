plugins {
    `faktorybot-module`
    `faktorybot-releasable-artifact`
}

description = "Generate factories to cleanly create and maintain test data"

dependencies {
    implementation("com.squareup:kotlinpoet:1.16.0")
    implementation("com.squareup:kotlinpoet-ksp:1.16.0")
    implementation(project(":faktory-bot-annotations"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.assertj:assertj-core:3.25.1")
    testImplementation(kotlin("reflect"))
}
