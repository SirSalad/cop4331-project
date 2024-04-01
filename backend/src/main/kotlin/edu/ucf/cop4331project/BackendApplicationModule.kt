package edu.ucf.cop4331project

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import edu.ucf.cop4331project.util.dataSourceJdbcUrl
import edu.ucf.cop4331project.util.dataSourcePassword
import edu.ucf.cop4331project.util.dataSourceUsername
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

@Suppress("unused")
class BackendApplicationModule(private val application: Application) : AbstractModule() {

    @Provides
    @Singleton
    fun provideDatasource(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = application.dataSourceJdbcUrl
            username = application.dataSourceUsername
            password = application.dataSourcePassword
        }

        return HikariDataSource(config)
            .also { it.validate() }
    }

    @Provides
    @Singleton
    fun provideDatabase(dataSource: HikariDataSource): Database {
        return Database.connect(dataSource)
    }

    override fun configure() {
        bind(Application::class.java).toInstance(application)
    }
}