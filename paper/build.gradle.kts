plugins {
    alias(libs.plugins.pluginyml)
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.miniplaceholders)
}

bukkit {
    main = "io.github.miniplaceholders.expansion.example.paper.PaperPlugin"
    apiVersion = "1.19"
    authors = listOf("4drian3d")
    depend = listOf("MiniPlaceholders")
    version = project.version as String
}
