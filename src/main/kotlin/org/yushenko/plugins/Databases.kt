package org.yushenko.plugins

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.yushenko.data.model.tables.CardTable
import org.yushenko.data.model.tables.UserTable
import org.yushenko.ext.getConfigProperty

object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbDriver = appConfig.getConfigProperty("db.driver")
    private val dbUrl = appConfig.getConfigProperty("db.url")
    private val dbUser = appConfig.getConfigProperty("db.username")
    private val dbPassword = appConfig.getConfigProperty("db.password")

    fun Application.initializationDatabase() {
        Database.connect(getHikariDataSource())

        transaction {
            SchemaUtils.create(
                UserTable, CardTable
            )
        }
    }

    private fun getHikariDataSource(): HikariDataSource {
        println("DB URL: $dbUrl")
        println("DB USER: $dbUser")

        val config = HikariConfig()
        config.driverClassName = dbDriver
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            transaction { block() }
        }
    }
}