plugins {
    alias(libs.plugins.blossom)
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.spark)
    implementation(project(":spark-expansion-common"))
}

blossom {
    replaceTokenIn("src/main/java/io/github/miniplaceholders/expansion/spark/velocity/Constants.java")
    replaceToken("{version}", project.version)
}
