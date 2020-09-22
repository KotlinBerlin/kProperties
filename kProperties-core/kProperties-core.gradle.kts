@file:Suppress("UNUSED_VARIABLE", "SpellCheckingInspection")

plugins {
    kotlin("multiplatform") version "1.4.10"
    `maven-publish`
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js {
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
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("stdlib"))
            }
        }

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
