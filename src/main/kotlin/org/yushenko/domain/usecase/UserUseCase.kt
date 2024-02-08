package org.yushenko.domain.usecase

import com.auth0.jwt.interfaces.JWTVerifier
import org.yushenko.authentication.JwtService
import org.yushenko.data.model.UserModel
import org.yushenko.domain.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {
    
    suspend fun createUser(userModel: UserModel) = userRepository.insertUser(user = userModel)

    suspend fun findUserByEmail(email: String) = userRepository.getUserByEmail(email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(user = userModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()
}