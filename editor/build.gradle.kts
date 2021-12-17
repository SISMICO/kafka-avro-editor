import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
}

group = "br.com.sismico"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven{
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation("org.apache.avro:avro:1.11.0")
    implementation("org.apache.avro:avro-compiler:1.11.0")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Kafka
    implementation("org.apache.kafka:kafka-clients:2.8.1")

    // Confluent
    implementation("io.confluent:kafka-schema-registry-client:6.1.0")
    implementation("io.confluent:kafka-streams-avro-serde:6.1.0")
    implementation("io.confluent:kafka-avro-serializer:5.2.1")

    // PODAM
    implementation("uk.co.jemos.podam:podam:7.2.7.RELEASE")

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