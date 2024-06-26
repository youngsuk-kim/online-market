@Suppress("ktlint:standard:property-naming")
val ktor_version: String by project
val kotlin_version: String by project
val hikaricp_version: String by project
val kotest_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.11"
}

group = "me.bread"
version = "0.0.1"

application {
    mainClass.set("me.bread.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {

    // core
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")

    // auth
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("org.mindrot:jbcrypt:0.4")

    // json
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")

    // logging
    implementation("org.slf4j:slf4j-api:2.0.13")

    // db
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")
    implementation("com.h2database:h2:2.1.214")
    implementation("io.github.flaxoos:ktor-server-kafka-jvm:1.+")

    // test
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
}
