import com.github.benmanes.gradle.versions.reporter.PlainTextReporter
import com.github.benmanes.gradle.versions.reporter.result.Result
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("multiplatform") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.ben-manes.versions") version "0.36.0"
    id("org.jetbrains.dokka") version "1.4.20"
    id("jacoco")
    java // jacoco doesn't works on multiplatform without this
}

group = "it.unito.kotac"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<DependencyUpdatesTask> {
    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+(-r)?$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }
    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }
    outputFormatter = closureOf<Result> {
        PlainTextReporter(project, revision, gradleReleaseChannel)
            .write(System.out, this)
        if (!this.outdated.dependencies.isEmpty() || this.gradle.current.isUpdateAvailable) {
            throw GradleException("Abort, there are dependencies to update.")
        }
    }
}

ktlint {
    version.set("0.40.0")
    disabledRules.set(setOf("no-wildcard-imports"))
}

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    js {
        browser()
        nodejs()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        jvm().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        js().compilations["main"].defaultSourceSet {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}

// see https://medium.com/@nwillc/kotlin-multiplatform-first-contact-bintray-jacoco-part-3-dbd496bf168a
tasks.withType<JacocoReport> {
    dependsOn("jvmTest")
    group = "Reporting"
    description = "Generate Jacoco coverage reports."
    val coverageSourceDirs = arrayOf(
        "commonMain/src",
        "jvmMain/src"
    )
    val classFiles = File("$buildDir/classes/kotlin/jvm/")
        .walkBottomUp()
        .toSet()
    classDirectories.setFrom(classFiles)
    sourceDirectories.setFrom(files(coverageSourceDirs))
    additionalSourceDirs.setFrom(files(coverageSourceDirs))
    executionData
        .setFrom(files("$buildDir/jacoco/jvmTest.exec"))
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination =
            File("$buildDir/jacoco-reports/html")
    }
}
