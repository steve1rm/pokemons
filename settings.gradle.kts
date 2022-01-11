include(":app", ":data")
rootProject.name = "Pokemon"

plugins {
    id("de.fayard.refreshVersions") version "0.23.0"
////                            # available:"0.30.0"
////                            # available:"0.30.1"
////                            # available:"0.30.2"
}
include(":domain")
