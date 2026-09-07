plugins {
    java
}

dependencies {
    compileOnly(libs.miniplaceholders)
    compileOnly(libs.spark)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
}

repositories {
    mavenCentral()
    maven("https://repo.lucko.me/")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(25))
tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(25)
    }
}
