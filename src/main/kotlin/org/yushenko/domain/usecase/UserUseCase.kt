package org.yushenko.domain.usecase

import org.yushenko.authentication.JwtService
import org.yushenko.data.model.UserModel
import org.yushenko.domain.repository.UserRepository

class UserUseCase(
    private val repositoryImpl: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = repositoryImpl.insertUser(user = userModel)

    suspend fun findUserByEmail(email: String) = repositoryImpl.getUserByEmail(email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(user = userModel)
}