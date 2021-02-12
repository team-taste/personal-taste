import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.31"

plugins {
	id("org.springframework.boot") version "2.1.17.RELEASE"
	id("io.spring.dependency-management") version "1.0.10.RELEASE"
	id("org.jetbrains.kotlin.plugin.noarg") version "1.2.71"
	kotlin("jvm") version "1.2.71"
	kotlin("plugin.spring") version "1.2.71"
}

apply(plugin = "kotlin-jpa")

group = "com.team-taste.personal-taste"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.microutils:kotlin-logging:1.7.6")
	runtime("com.h2database:h2")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlin:kotlin-noarg:${kotlinVersion}")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "junit")
	}
	testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.3")
	testImplementation("io.kotlintest:kotlintest-extensions-spring:3.3.3")
	testImplementation("io.mockk:mockk:1.9.1")
	testImplementation("org.junit.jupiter:junit-jupiter-api")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
