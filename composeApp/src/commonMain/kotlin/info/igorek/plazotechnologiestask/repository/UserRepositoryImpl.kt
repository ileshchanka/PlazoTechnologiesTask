package info.igorek.plazotechnologiestask.repository

import info.igorek.plazotechnologiestask.defaultComponent.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface UserRepository {
    val user: StateFlow<User>

    suspend fun updateUserName(newName: String): User
    suspend fun reloadUser(): User
}


class UserRepositoryImpl(val scope: CoroutineScope) : UserRepository {

    private val _user = MutableStateFlow(User())
    override val user: StateFlow<User> = _user.asStateFlow()

    override suspend fun updateUserName(newName: String): User {
        return scope.async(Dispatchers.Default) {
            delay(300)
            val currentUser = _user.value
            val updatedUser = currentUser.copy(fullName = newName)
            _user.value = updatedUser
            updatedUser
        }.await()
    }

    override suspend fun reloadUser(): User {
        return scope.async(Dispatchers.Default) {
            delay(300)
            val user = User()
            _user.value = user
            user
        }.await()
    }
}
