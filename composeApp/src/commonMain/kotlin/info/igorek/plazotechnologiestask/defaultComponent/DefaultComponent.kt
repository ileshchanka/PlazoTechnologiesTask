package info.igorek.plazotechnologiestask.defaultComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.statekeeper.ExperimentalStateKeeperApi
import info.igorek.plazotechnologiestask.repository.UserRepository
import info.igorek.plazotechnologiestask.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultComponent(
    context: ComponentContext,
    private val userRepository: UserRepository = UserRepositoryImpl(),
) : ComponentContext by context {

    @Serializable
    data class State(
        val user: User,
        val isLoading: Boolean = false,
    )

    private val scope = CoroutineScope(Dispatchers.Default)

    @OptIn(ExperimentalStateKeeperApi::class)
    private val _state = MutableValue(
        stateKeeper.consume(key = "STATE", strategy = State.serializer())
            ?: State(user = User.EMPTY)
    )
    val state: Value<State> = _state

    init {
        stateKeeper.register(key = "STATE", strategy = State.serializer()) {
            _state.value
        }

        if (_state.value.user == User.EMPTY) {
            _state.value = _state.value.copy(isLoading = true)
            scope.launch {
                val user = userRepository.loadProfile()
                _state.value = _state.value.copy(user = user, isLoading = false)
            }
        }
    }

    private val navigation = SlotNavigation<Config>()

    val slots = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        childFactory = ::createChildSlots,
    )

    fun createChildSlots(config: Config, context: ComponentContext): Child {
        return when (config) {
            is Config.UpdateName -> Child.UpdateName
        }
    }

    fun showUpdateNameDialog() {
        navigation.activate(Config.UpdateName)
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object UpdateName : Config
    }

    sealed interface Child {
        data object UpdateName : Child
    }

    fun onConfirmUpdateNameDialog(newName: String) {
        if (newName.isNotBlank()) {
            _state.value = _state.value.copy(isLoading = true)
            scope.launch {
                val updatedUser = userRepository.updateName(newName)
                _state.value = _state.value.copy(user = updatedUser, isLoading = false)
            }
        }
        dismissUpdateNameDialog()
    }

    fun dismissUpdateNameDialog() = navigation.dismiss()

    fun onReloadUserClick() {
        _state.value = _state.value.copy(isLoading = true)
        scope.launch {
            val reloadedUser = userRepository.reloadUser()
            _state.value = _state.value.copy(user = reloadedUser, isLoading = false)
        }
    }
}
