object Version {
    const val SPRING_BOOT = "3.1.4"
    const val SLF4J = "2.0.0"
    const val LOGBACK = "1.4.5"
    const val JACKSON = "2.15.2"
    const val MAPSTRUCT = "1.6.0"
    const val LOMBOK = "1.18.30"
    const val FLYWAY = "9.16.0"
    const val JUNIT = "5.10.0"
    const val ASSERTJ_CORE = "3.24.2"
    const val MOCKITO = "4.7.0"
    const val JAXB_RUNTIME = "2.3.1"
    const val SPRING_SHELL = "3.4.0"
    const val GSON_BUILDER = "2.11.0"

    object Lombok {
        const val MAP_STRUCT_BINDING = "0.2.0"
    }
}

plugins {
    id("java")
    id("com.diffplug.spotless") version "6.23.3"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.autonomousapps.dependency-analysis") version "2.8.0"
}

group = "ru.hofftech"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("ch.qos.logback:logback-classic:${Version.LOGBACK}")
    runtimeOnly("com.fasterxml.jackson.core:jackson-databind:${Version.JACKSON}")
    runtimeOnly("com.fasterxml.jackson.core:jackson-annotations:${Version.JACKSON}")
    runtimeOnly("com.fasterxml.jackson.core:jackson-core:${Version.JACKSON}")
    runtimeOnly("org.glassfish.jaxb:jaxb-runtime:${Version.JAXB_RUNTIME}")

    compileOnly("org.projectlombok:lombok:${Version.LOMBOK}")
    annotationProcessor("org.projectlombok:lombok:${Version.LOMBOK}")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:${Version.Lombok.MAP_STRUCT_BINDING}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${Version.MAPSTRUCT}")

    implementation("org.springframework.shell:spring-shell-starter")
    implementation("org.slf4j:slf4j-api:${Version.SLF4J}")
    implementation("org.springframework.boot:spring-boot-starter-web:${Version.SPRING_BOOT}")

    testCompileOnly("org.projectlombok:lombok:${Version.LOMBOK}")
    testAnnotationProcessor("org.projectlombok:lombok:${Version.LOMBOK}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${Version.SPRING_BOOT}")
    testImplementation(platform("org.junit:junit-bom:${Version.JUNIT}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:${Version.ASSERTJ_CORE}")
    testImplementation("org.mockito:mockito-inline:${Version.MOCKITO}")
    testImplementation("org.mockito:mockito-core:${Version.MOCKITO}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Version.MOCKITO}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Version.JUNIT}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.shell:spring-shell-dependencies:3.4.0")
    }
}

spotless {
    java {
        removeUnusedImports()
    }
}

tasks.test {
    useJUnitPlatform()
}