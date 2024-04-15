package edu.ucf.cop4331project.storage

import edu.ucf.cop4331project.common.container.Container
import edu.ucf.cop4331project.common.storage.User
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.upsert

@Container
class UserRepository : IdTable<String>("user_repository") {
    override val id = varchar("username", 50).entityId()
    val password = varchar("password", 50)
    val coins = integer("coins").default(0)
    val premium = bool("premium").default(false)

    override val primaryKey = PrimaryKey(id)

    suspend fun findByUsernameAndPassword(username: String, password: String): User? = newSuspendedTransaction {
        selectAll()
            .where { this@UserRepository.id eq username }
            .andWhere { this@UserRepository.password eq password }
            .limit(1)
            .map { row -> User(username, password, row[coins], row[premium]) }
            .singleOrNull()
    }

    suspend fun findByUsername(username: String): User? = newSuspendedTransaction {
        selectAll()
            .where { this@UserRepository.id eq username }
            .limit(1)
            .map { row -> User(username, row[password], row[coins], row[premium]) }
            .singleOrNull()
    }

    suspend fun update(user: User) = newSuspendedTransaction {
        upsert {
            it[id] = user.username
            it[password] = user.password
            it[coins] = user.coins
            it[premium] = user.isPremium
        }
    }
}
