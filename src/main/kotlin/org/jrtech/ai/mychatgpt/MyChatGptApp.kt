package org.jrtech.ai.mychatgpt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyChatGptApp

fun main(args: Array<String>) {
    runApplication<MyChatGptApp>(*args)
}