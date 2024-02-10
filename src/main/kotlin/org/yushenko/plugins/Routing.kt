package org.yushenko.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.yushenko.domain.usecase.CardUseCase
import org.yushenko.domain.usecase.UserUseCase
import org.yushenko.routes.CardRoute
import org.yushenko.routes.UserRoute

fun Application.configureRouting(userUseCase: UserUseCase, cardUseCase: CardUseCase) {
    routing {
        UserRoute(userUseCase = userUseCase)
        CardRoute(cardUseCase = cardUseCase)
    }
}