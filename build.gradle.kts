import com.github.benmanes.gradle.versions.reporter.PlainTextReporter
import com.github.benmanes.gradle.versions.reporter.result.Result
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.github.ben-manes.versions") version "0.28.0"
    id("jacoco")
}

group = "it.unito.kotac"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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
    version.set("0.37.0")
    disabledRules.set(setOf("no-wildcard-imports"))
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("junit", "junit", "4.13")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
