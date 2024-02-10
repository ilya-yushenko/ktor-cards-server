package org.yushenko.ext

import io.ktor.server.config.*

fun HoconApplicationConfig.getConfigProperty(path: String): String =
    this.property(path).getString()