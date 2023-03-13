rootProject.name = "Example-Expansion"

arrayOf("paper", "velocity").forEach {
    include("example-expansion-$it")

    project(":example-expansion-$it").projectDir = file(it)
}
