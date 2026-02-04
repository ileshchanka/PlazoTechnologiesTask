package info.igorek.plazotechnologiestask.defaultComponent

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class DefaultComponent(
    context: ComponentContext,
) : ComponentContext by context {

    data class State(val user: User = User.DEFAULT_USER)

    private val _state = MutableValue(State())
    val state: Value<State> = _state

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