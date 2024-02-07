package org.yushenko

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.yushenko.plugins.DatabaseFactory.initializationDatabase
import org.yushenko.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    initializationDatabase()
    configureRouting()
}