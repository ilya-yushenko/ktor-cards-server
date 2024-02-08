package org.yushenko.domain.repository

import org.yushenko.data.model.UserModel

interface UserRepository {
    suspend fun getUserByEmail(email: String): UserModel?
    suspend fun insertUser(user: UserModel)
}