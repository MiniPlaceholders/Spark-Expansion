rootProject.name = "Spark-Expansion"

arrayOf("paper", "velocity", "common").forEach {
    include("spark-expansion-$it")
    project(":spark-expansion-$it").projectDir = file(it)
}
