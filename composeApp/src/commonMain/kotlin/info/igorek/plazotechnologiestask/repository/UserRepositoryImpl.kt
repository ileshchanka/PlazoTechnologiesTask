package info.igorek.plazotechnologiestask.repository

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import info.igorek.plazotechnologiestask.defaultComponent.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

interface UserRepository {
    val user: Value<User>

    suspend fun updateUserName(newName: String): User
    suspend fun reloadUser(): User
}


class UserRepositoryImpl(val scope: CoroutineScope) : UserRepository {

    private val _user = MutableValue(User.DEFAULT_USER)
    override val user: Value<User> = _user

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
            val user = User.DEFAULT_USER
            _user.value = user
            user
        }.await()
    }
}
