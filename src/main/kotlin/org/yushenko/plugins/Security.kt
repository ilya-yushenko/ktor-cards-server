package org.yushenko.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.yushenko.domain.usecase.UserUseCase

fun Application.configureSecurity(userUseCase: UserUseCase) {
    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJwtVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email = email)
                user
            }
        }
    }
}