package info.igorek.plazotechnologiestask.defaultComponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.igorek.plazotechnologiestask.updateName.UpdateNameDialogUi

@Composable
fun DefaultUi(component: DefaultComponent) {

    val slots by component.slots.subscribeAsState()
    val state by component.state.subscribeAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = state.user.fullName,
                modifier = Modifier,
            )

            Button(
                onClick = {
                    component.showUpdateNameDialog()
                },
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text("Update Name")
            }

            if (state.isLoading) {
                Box(
                    modifier = Modifier.padding(top = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    when (slots.child?.instance) {
        is DefaultComponent.Child.UpdateName -> {
            UpdateNameDialogUi(
                currentName = state.user.fullName,
                onDismiss = component::dismissUpdateNameDialog,
                onConfirm = component::onConfirmUpdateNameDialog,
            )
        }

        null -> {}
    }
}
