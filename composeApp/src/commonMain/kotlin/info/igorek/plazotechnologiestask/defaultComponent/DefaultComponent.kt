package info.igorek.plazotechnologiestask.defaultComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import info.igorek.plazotechnologiestask.repository.UserRepository
import info.igorek.plazotechnologiestask.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultComponent(
    context: ComponentContext,
    private val userRepository: UserRepository = UserRepositoryImpl(CoroutineScope(Dispatchers.Default)),
) : ComponentContext by context {

    data class State(
        val user: User,
        val isLoading: Boolean = false,
    )

    private val scope = CoroutineScope(Dispatchers.Default)
    private val _state = MutableValue(State(user = User.DEFAULT_USER))
    val state: Value<State> = _state

    init {
        scope.launch {
            val user = userRepository.getName()
            _state.value = _state.value.copy(user = user)
        }
    }

    private val navigation = SlotNavigation<Config>()

    val slots = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
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
            val updatedUser = _state.value.user.copy(fullName = newName)
            _state.value = _state.value.copy(user = updatedUser)
        }
        dismissUpdateNameDialog()
    }

    fun dismissUpdateNameDialog() = navigation.dismiss()
}
