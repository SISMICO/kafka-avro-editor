import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
}

group = "br.com.sismico"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.avro:avro:1.11.0")
    implementation("org.apache.avro:avro-compiler:1.11.0")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}