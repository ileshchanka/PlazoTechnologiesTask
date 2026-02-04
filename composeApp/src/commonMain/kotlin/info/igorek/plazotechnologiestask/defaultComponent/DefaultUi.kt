package info.igorek.plazotechnologiestask.defaultComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.igorek.plazotechnologiestask.updateName.UpdateNameDialogUi

@Composable
fun DefaultUi(component: DefaultComponent) {

    val slots by component.slots.subscribeAsState()
    val state by component.state.subscribeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.user.fullName,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        TextButton(
            onClick = {
                component.showUpdateNameDialog()
            },
            modifier = Modifier
                .background(Color.Cyan, RoundedCornerShape(16.dp))
        ) {
            Text("Update Name")
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
