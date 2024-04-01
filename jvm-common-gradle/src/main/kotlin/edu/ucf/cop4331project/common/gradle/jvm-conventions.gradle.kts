package edu.ucf.cop4331project.common.gradle

plugins {
    `java-library`
    application
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
    id("io.freefair.lombok")
    kotlin("plugin.lombok")
}

group = "edu.ucf.cop4331project"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

configure<JavaPluginExtension> {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
    withJavadocJar()
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
        options.compilerArgs.add("-parameters")
    }

    withType<ProcessResources> {
        filteringCharset = Charsets.UTF_8.name()
    }

    withType<Javadoc> {
        options.encoding = Charsets.UTF_8.name()
    }
}
