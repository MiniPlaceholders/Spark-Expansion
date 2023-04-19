dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
    implementation(project(":spark-expansion-common"))
}

tasks {
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
}
