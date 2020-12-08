package com.tcs.service

import khttp.get
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class ServiceKotlinTemplateApplication

fun main(args: Array<String>) {

    runApplication<ServiceKotlinTemplateApplication>(*args)
}
