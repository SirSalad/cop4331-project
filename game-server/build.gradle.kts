plugins {
    id("edu.ucf.cop4331project.common.gradle.jvm-conventions")
    id("io.ktor.plugin") version "2.3.9"
}

dependencies {
    // Commons
    implementation("edu.ucf.cop4331project:jvm-common")
    annotationProcessor("edu.ucf.cop4331project:jvm-common")
    //kapt("edu.ucf.cop4331project:jvm-common")

    // Kotlin platform
    implementation(kotlin("stdlib-jdk8"))

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.3")

    // Ktor
    // io.ktor:ktor-server% BOM is provided by the io.ktor.plugin plugin
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-server-auth")
    implementation("io.ktor:ktor-server-auth-jwt")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-websockets")
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}