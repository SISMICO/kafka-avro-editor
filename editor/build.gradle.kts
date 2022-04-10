import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    application
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.2.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("org.flywaydb.flyway") version "8.5.7"
}

group = "br.com.sismico.kafka-avro-editor"
version = "1.0-SNAPSHOT"
var logback_version = "1.2.10"
var ktorm_version = "3.4.1"
var postgresql_version = "42.3.3"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation("org.apache.avro:avro:1.11.0")
    implementation("org.apache.avro:avro-compiler:1.11.0")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    //implementation("org.slf4j:slf4j-api:1.7.32")
    //implementation("org.slf4j:slf4j-simple:1.7.32")

//    implementation("com.fasterxml.jackson.core:jackson-databind")
//    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")

    // Kafka
    implementation("org.apache.kafka:kafka-clients:2.8.1")

    // Confluent
    implementation("io.confluent:kafka-schema-registry-client:6.1.0")
    implementation("io.confluent:kafka-streams-avro-serde:6.1.0")
    implementation("io.confluent:kafka-avro-serializer:5.2.1")

    // PODAM
    implementation("uk.co.jemos.podam:podam:7.2.7.RELEASE")

    // Fuel
    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")

    // Apache Commons Cli
    implementation("commons-cli:commons-cli:1.5.0")

    // KTorm, Flyway + Postgres
    implementation("org.flywaydb:flyway-core:8.5.7")
    implementation("org.ktorm:ktorm-core:$ktorm_version")
    implementation("org.ktorm:ktorm-support-postgresql:$ktorm_version")
    implementation("org.postgresql:postgresql:$postgresql_version")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask> {
    reportsOutputDirectory.set(
        project.layout.buildDirectory.dir("other/location/$name")
    )
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

application {
    mainClass.set("MainKt")
}
