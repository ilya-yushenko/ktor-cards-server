package org.yushenko.authentication

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import io.ktor.util.*
import org.yushenko.ext.getConfigProperty
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


private val appConfig = HoconApplicationConfig(ConfigFactory.load())
private val hashKey = appConfig.getConfigProperty("security.key").toByteArray()
private val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String {
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)

    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}