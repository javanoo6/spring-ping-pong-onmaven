package com.example.javanoo6

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@AutoConfiguration
@SpringBootApplication(scanBasePackages = ["com.example.javanoo6"])
class SpringPingPongOnMavenApplication

fun main(args: Array<String>) {
	runApplication<SpringPingPongOnMavenApplication>(*args)
}
