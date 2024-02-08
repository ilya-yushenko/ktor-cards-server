package org.yushenko.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.yushenko.domain.usecase.CardUseCase
import org.yushenko.domain.usecase.UserUseCase
import org.yushenko.routes.userRoute

fun Application.configureRouting(userUseCase: UserUseCase, cardUseCase: CardUseCase) {
    routing {
        userRoute(userUseCase = userUseCase)
    }
}