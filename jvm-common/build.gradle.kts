plugins {
    id("edu.ucf.cop4331project.common.gradle.jvm-conventions")
}

dependencies {
    api("org.projectlombok:lombok:1.18.30")
    api("com.google.inject:guice:7.0.0")
    api("org.spongepowered:configurate-yaml:4.1.2")
    api("org.spongepowered:configurate-extra-kotlin:4.1.2")
}