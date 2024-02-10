package org.yushenko.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.yushenko.data.model.UserModel
import org.yushenko.ext.getConfigProperty
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val issuer = appConfig.getConfigProperty("jwt.issuer")
    private val secret = appConfig.getConfigProperty("jwt.secret")
    private val algorithm = Algorithm.HMAC512(secret)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: UserModel): String {
        return JWT.create()
            .withSubject("CardsAppAuthentication")
            .withIssuer(issuer)
            .withClaim("email", user.email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier
}