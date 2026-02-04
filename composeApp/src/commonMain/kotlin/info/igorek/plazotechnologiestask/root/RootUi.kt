package info.igorek.plazotechnologiestask.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.igorek.plazotechnologiestask.defaultComponent.DefaultUi

@Composable
fun RootUi(component: RootComponent) {

    val stack by component.stack.subscribeAsState()

    Children(
        stack = stack,
        modifier = Modifier,
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Default -> DefaultUi(child.component)
        }
    }
}
