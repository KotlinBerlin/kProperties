@file:Suppress("UNUSED_VARIABLE", "SpellCheckingInspection")

import org.apache.commons.io.FileUtils
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

plugins {
    kotlin("multiplatform") version "1.4.10"
    id("maven-publish")
    id("org.jetbrains.dokka") version "0.10.1"
}

group = "de.kotlin-berlin"
version = "2.0-FINAL"

repositories {
    jcenter()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js {
        browser()
        nodejs()
    }
    mingwX64()
    mingwX86()

    androidNativeArm32()
    androidNativeArm64()

    watchosArm32()
    watchosArm64()
    watchosX86()

    iosArm32()
    iosArm64()
    iosX64()

    tvosArm64()
    tvosX64()

    macosX64()

    wasm32()

    linuxArm64()
    linuxArm32Hfp()
    linuxMips32()
    linuxMipsel32()
    linuxX64()

    sourceSets {
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("commons-io:commons-io:2.7")
    }
}

tasks {
    val tempPath = project.findProperty("documentation.path")?.toString()
    val tempFormat = project.findProperty("documentation.format")?.toString()

    clean {
        doLast {
            val tempRootDir = File(buildDir.parentFile, "docs")
            FileUtils.deleteDirectory(tempRootDir)
        }
    }

    val dokka by getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = tempFormat ?: "html"
        outputDirectory = tempPath ?: "$buildDir/dokka"

        doFirst {
            val tempRootDir = File(projectDir, "docs")

            tempRootDir.listFiles()?.forEach(FileUtils::forceDelete)
        }

        doLast {
            val tempRootDir = File(projectDir, "docs/k-properties")

            for (it in FileUtils.iterateFiles(tempRootDir, null, true)) {
                var tempContent =
                        FileUtils.readFileToString(it, Charset.defaultCharset())
                tempContent = tempContent.replace("../style.css", "style.css")
                FileUtils.writeStringToFile(it, tempContent, Charset.defaultCharset(), false)
            }

            tempRootDir.listFiles()?.forEach {
                if (it.isFile) {

                    var tempContent =
                            FileUtils.readFileToString(it, Charset.defaultCharset())
                    tempContent = tempContent.replace("../style.css", "style.css")
                    FileUtils.writeStringToFile(it, tempContent, Charset.defaultCharset(), false)
                    FileUtils.moveFileToDirectory(it, tempRootDir.parentFile, true)
                } else {
                    FileUtils.moveDirectoryToDirectory(it, tempRootDir.parentFile, true)
                }
            }
            tempRootDir.delete()

            val stdout = ByteArrayOutputStream()
            exec {
                commandLine = listOf("git", "add", "./docs")
                standardOutput = stdout
                errorOutput = stdout
            }

            try {
                exec {
                    commandLine = listOf("git", "commit", "./docs", "-m Dokumentation neu erzeugt")
                    standardOutput = stdout
                    errorOutput = stdout
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            println(String(stdout.toByteArray()))
        }
    }
}
