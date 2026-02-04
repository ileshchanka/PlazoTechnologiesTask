package info.igorek.plazotechnologiestask.defaultComponent

import com.arkivanov.decompose.ComponentContext
import info.igorek.plazotechnologiestask.repository.UserRepository
import info.igorek.plazotechnologiestask.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DefaultComponent(context: ComponentContext) : ComponentContext by context {

    data class State(
        val user: User = User(),
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Default)
    private val repository: UserRepository = UserRepositoryImpl(scope)


    fun onUpdateNameClick(newName: String) {
        scope.launch {
            val updatedUser = repository.updateUserName(newName)
            _state.value = _state.value.copy(user = updatedUser)
        }
    }

    fun onReloadUserClick() {
        scope.launch {
            val reloadedUser = repository.reloadUser()
            _state.value = State(user = reloadedUser)
        }
    }
}