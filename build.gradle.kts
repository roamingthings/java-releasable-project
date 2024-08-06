plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.axion)
    alias(libs.plugins.spotless)
}

repositories {
    mavenLocal()
    mavenCentral()
}

scmVersion {
    tag {
        prefix.set("v")
    }
}

project.group = "de.roamingthings"
project.version = scmVersion.version

dependencies {
    annotationProcessor(libs.google.auto.core)
    implementation(libs.mapstruct.core)
    implementation(libs.google.auto.core)
    compileOnly(libs.google.auto.annotations)

    testImplementation(libs.assertj.core)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.junit.jupiter)
    testImplementation(libs.snapshotTesting.junit5)
    testImplementation(libs.log4j2.core)
    testImplementation(libs.log4j2.slf4j.impl)

    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    val sourcesJar by creating(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
    val javadocJar by creating(Jar::class) {
        dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }
    test {
        useJUnitPlatform()
    }
    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
        archives(jar)
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url =
                uri("https://maven.pkg.github.com/${property("github.package-registry.owner")}/${property("github.package-registry.repository")}")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("MapStruct Documentation Processor")
                description.set("Annotation processor to generate documentation from MapStruct mappers.")
                url.set("https://github.com/${property("github.package-registry.owner")}/${property("github.package-registry.repository")}")

                scm {
                    connection.set("https://github.com/${property("github.package-registry.owner")}/${property("github.package-registry.repository")}.git")
                    developerConnection.set(
                        "https://github.com/${property("github.package-registry.owner")}/${
                            property(
                                "github.package-registry.repository"
                            )
                        }.git"
                    )
                    url.set("https://github.com/${property("github.package-registry.owner")}/${property("github.package-registry.repository")}")
                }
            }
        }
    }
}



