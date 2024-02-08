package org.yushenko.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import org.yushenko.authentication.JwtService
import org.yushenko.data.model.RoleModel
import org.yushenko.data.model.UserModel
import org.yushenko.data.repository.UserRepositoryImpl
import org.yushenko.domain.usecase.UserUseCase

fun Application.configureSecurity() {

    val jvtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jvtService)

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "i.yushenko@gmail.com",
                login = "ilya_yushenko",
                password = "qwerty123",
                firstName = "Ilya",
                lastName = "Yushenko",
                isActive = true,
                role = RoleModel.MODERATOR
            )
        )
    }

    authentication {
        jwt("jwt") {
            verifier(jvtService.getVerifier())
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