import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
}

group = "io.ryecrow"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
    mavenCentral()
}

dependencies {
    implementation("commons-io:commons-io:2.8.0")
    implementation("org.apache.commons:commons-lang3:3.11")
    implementation("jakarta.mail:jakarta.mail-api:2.0.0")
    implementation(kotlin("stdlib-jdk8"))

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

buildscript {
    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public/") }
        mavenCentral()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
