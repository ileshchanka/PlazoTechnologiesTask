package info.igorek.plazotechnologiestask.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.igorek.plazotechnologiestask.defaultComponent.DefaultUi

@Composable
fun RootUi(component: RootComponent) {

    val stack by component.stack.subscribeAsState()

    Scaffold { paddingValues ->
        Children(
            stack = stack,
            modifier = Modifier.padding(paddingValues),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Default -> DefaultUi(child.component)
            }
        }
    }
}
