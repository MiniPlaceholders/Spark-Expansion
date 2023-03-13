plugins {
    alias(libs.plugins.pluginyml)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
    implementation(project(":spark-expansion-common"))
}

bukkit {
    main = "io.github.miniplaceholders.expansion.spark.paper.PaperPlugin"
    apiVersion = "1.19"
    authors = listOf("4drian3d")
    depend = listOf("MiniPlaceholders", "spark")
    version = project.version as String
}
