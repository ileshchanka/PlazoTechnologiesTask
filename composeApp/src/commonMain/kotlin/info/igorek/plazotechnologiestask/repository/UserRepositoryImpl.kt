package info.igorek.plazotechnologiestask.repository

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import info.igorek.plazotechnologiestask.defaultComponent.User
import kotlinx.coroutines.delay

interface UserRepository {
    val user: Value<User>

    suspend fun updateName(name: String): User
    suspend fun reloadUser(): User
    suspend fun loadProfile(): User
}

class UserRepositoryImpl : UserRepository {

    private val _user = MutableValue(User.DEFAULT)
    override val user: Value<User> = _user

    override suspend fun updateName(name: String): User {
        delay(300)
        val updatedUser = _user.value.copy(fullName = name)
        _user.value = updatedUser
        return updatedUser
    }

    override suspend fun reloadUser(): User {
        val user = loadProfile()
        _user.value = user
        return user
    }

    override suspend fun loadProfile(): User {
        delay(300)
        val user = _user.value
        return user
    }
}
