plugins {
	java
	id("org.springframework.boot") version "3.5.0-SNAPSHOT"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.9.0"
    kotlin("jvm")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.hibernate:hibernate-core:6.2.0.Final")
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation(kotlin("stdlib"))
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
	//implementation("org.springframework.boot:spring-boot-starter-web") // Para crear APIs REST
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Para acceder a la base de datos
	implementation("mysql:mysql-connector-java:8.0.32")
    implementation(kotlin("stdlib-jdk8"))
	testImplementation(kotlin("test"))
	testImplementation("org.mockito:mockito-core:4.8.0")
	testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
	testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
