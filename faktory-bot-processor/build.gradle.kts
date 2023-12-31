plugins {
    `faktorybot-module`
    `faktorybot-releasable-artifact`
}

description = "Generate factories to cleanly create and maintain test data"

dependencies {
    implementation("com.squareup:kotlinpoet:1.15.3")
    implementation("com.squareup:kotlinpoet-ksp:1.15.3")
    implementation(project(":faktory-bot-annotations"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.16")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testImplementation("org.assertj:assertj-core:3.25.0")
    testImplementation(kotlin("reflect"))
}
