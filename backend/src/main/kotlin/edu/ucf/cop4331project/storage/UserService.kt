package edu.ucf.cop4331project.storage

import com.google.inject.Inject
import com.google.inject.Singleton
import edu.ucf.cop4331project.common.storage.User

@Singleton
class UserService @Inject constructor(private val userRepository: UserRepository) {

    suspend fun loadByUsernameAndPassword(username: String, password: String): User? =
        userRepository.findByUsernameAndPassword(username, password)

    suspend fun loadByUsername(username: String): User? =
        userRepository.findByUsername(username)

    suspend fun update(user: User) =
        userRepository.update(user)
}