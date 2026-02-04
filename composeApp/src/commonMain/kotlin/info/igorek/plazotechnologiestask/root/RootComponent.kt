package info.igorek.plazotechnologiestask.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import info.igorek.plazotechnologiestask.defaultComponent.DefaultComponent
import kotlinx.serialization.Serializable

class RootComponent(context: ComponentContext) : ComponentContext by context {

    private val navigation = StackNavigation<Config>()

    val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Default,
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(config: Config, context: ComponentContext): Child =
        when (config) {
            is Config.Default -> Child.Default(DefaultComponent(context))
        }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Default : Config
    }

    sealed interface Child {
        class Default(val component: DefaultComponent) : Child
    }


}
