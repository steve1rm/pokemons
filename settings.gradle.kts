include(":app", ":data")
rootProject.name = "Pokemon"

plugins {
    id("de.fayard.refreshVersions") version "0.23.0"
}
include(":domain")
include(":detektCustomRules")
