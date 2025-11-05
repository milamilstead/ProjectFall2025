plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jfree:jfreechart:1.5.4")
    implementation("org.json:json:20230227")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.json:json:20230227") // for JSON parsing in display
    implementation("org.mongodb:mongodb-driver-sync:4.10.2")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    // or for newer SLF4J 2.x versions:
    // implementation("org.slf4j:slf4j-simple:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}