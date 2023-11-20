import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://kotlinlang.org/docs/jvm-get-started.html#run-the-application
    kotlin("jvm") version "1.9.20"
    application
}

group = "com.hades.example.leankotlin"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven {
        url = uri("https://repo.nju.edu.cn/repository/maven-public")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/google")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/central")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
    maven {
        url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
    }
    maven {
        url = uri("https://www.jitpack.io")
    }
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
    kotlinOptions.jvmTarget = "11"
}

//application {
//    mainClass.set("MainKt")
//}