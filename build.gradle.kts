plugins {
    id("java")
}

group = "ru.hofftech.delivery"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("org.slf4j:slf4j-api:2.0.0")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.0")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")
}

tasks.test {
    useJUnitPlatform()
}