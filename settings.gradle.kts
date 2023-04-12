plugins {
    id("com.gradle.enterprise") version ("3.13")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}

rootProject.name = "faktory-bot"

include("faktory-bot-processor")
include("faktory-bot-annotations")
include("example-app")