package org.yushenko

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.yushenko.authentication.JwtService
import org.yushenko.data.repository.CardRepositoryImpl
import org.yushenko.data.repository.UserRepositoryImpl
import org.yushenko.domain.usecase.CardUseCase
import org.yushenko.domain.usecase.UserUseCase
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

    val jvtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jvtService)
    val cardUseCase = CardUseCase(cardRepository)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting()
}