import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://kotlinlang.org/docs/jvm-get-started.html#run-the-application
    kotlin("jvm") version "1.8.21"
    application
}

group = "com.hades.example.leankotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/javax.inject/javax.inject
    implementation("javax.inject:javax.inject:1")
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