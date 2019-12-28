project.group = "com.openfaas.kotlin.template"

plugins {
    kotlin("jvm") version "1.3.50"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.http4k:http4k-core:3.194.0")
    implementation("org.http4k:http4k-format-jackson:3.194.0")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.1")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}