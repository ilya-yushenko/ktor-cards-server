package org.yushenko

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.yushenko.plugins.DatabaseFactory.initializationDatabase
import org.yushenko.plugins.configureMonitoring
import org.yushenko.plugins.configureRouting
import org.yushenko.plugins.configureSecurity
import org.yushenko.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity()
    configureRouting()
}