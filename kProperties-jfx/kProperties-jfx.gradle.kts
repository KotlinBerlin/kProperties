plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}

val jfxVersion = 15

dependencies {
    val hostOs = System.getProperty("os.name")
    val platformIdentifier = when {
        hostOs == "Mac OS X" -> "mac"
        hostOs == "Linux" -> "linux"
        hostOs.startsWith("Windows") -> "win"
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    compileOnly("org.openjfx:javafx-base:$jfxVersion:$platformIdentifier")
    testImplementation(kotlin("test-junit"))
    testImplementation("org.openjfx:javafx-base:$jfxVersion:$platformIdentifier")
    api(project(":kProperties-core"))
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
