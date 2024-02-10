package org.yushenko.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.yushenko.domain.usecase.UserUseCase
import org.yushenko.ext.getConfigProperty

fun Application.configureSecurity(userUseCase: UserUseCase) {
    val appConfig = HoconApplicationConfig(ConfigFactory.load())
    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJwtVerifier())
            realm = appConfig.getConfigProperty("jwt.realm")
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email = email)
                user
            }
        }
    }
}