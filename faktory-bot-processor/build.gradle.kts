plugins {
    `faktorybot-module`
    `faktorybot-releasable-artifact`
}

description = "Generate factories to cleanly create and maintain test data"

dependencies {
    implementation("com.squareup:kotlinpoet:1.17.0")
    implementation("com.squareup:kotlinpoet-ksp:1.17.0")
    implementation(project(":faktory-bot-annotations"))

    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.22")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.26.0")
    testImplementation(kotlin("reflect"))
}
