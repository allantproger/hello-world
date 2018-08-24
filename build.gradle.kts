import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.2.50"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version "2.0.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
}

version = "1.0"

tasks {
    withType<Jar> {
        archiveName = "client-bot.jar"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

kotlin.experimental.coroutines = ENABLE

dependencies {
    compile("org.springframework.boot:spring-boot-starter")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.telegram:telegrambots-spring-boot-starter:3.6.1")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.23.3")
    compile("org.telegram:telegrambots:3.6.1")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    compile("org.springframework:spring-web")
    compile ("org.jsoup:jsoup:1.11.3")
    compile ("org.springframework.boot:spring-boot-starter-web")
}

repositories {
    mavenCentral()
    jcenter()
}