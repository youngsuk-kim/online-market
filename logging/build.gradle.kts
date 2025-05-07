import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val sentryVersion = "8.11.1"

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
    id("org.jlleitschuh.gradle.ktlint").version("12.1.0")
}

group = "me.bread"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.sentry:sentry-logback:${sentryVersion}")
    implementation ("net.logstash.logback:logstash-logback-encoder:7.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
