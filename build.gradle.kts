import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://kotlinlang.org/docs/jvm-get-started.html#run-the-application
    kotlin("jvm") version "1.8.10"
    application
}

group = "com.hades.example.leankotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

//application {
//    mainClass.set("MainKt")
//}