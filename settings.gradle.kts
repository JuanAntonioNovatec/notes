pluginManagement {
	repositories {
		maven { url = uri("https://repo.spring.io/milestone") }
		maven { url = uri("https://repo.spring.io/snapshot") }
		gradlePluginPortal()
	}
    plugins {
        kotlin("jvm") version "2.1.20"
    }
}
rootProject.name = "demo"
