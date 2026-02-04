package info.igorek.plazotechnologiestask.defaultComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = Color(0xFF6366F1),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = state.user.fullName.firstOrNull()?.uppercase() ?: "U",
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // User Name
            Text(
                text = state.user.fullName,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Text(
                text = state.user.email,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF6B7280),
                ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Update Name Button
            Button(
                onClick = {
                    component.showUpdateNameDialog()
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6366F1),
                ),
            ) {
                Text(
                    "Update Name",
                    modifier = Modifier.padding(8.dp),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Reload User Button
            Button(
                onClick = {
                    component.onReloadUserClick()
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF10B981),
                ),
            ) {
                Text(
                    "Reload User",
                    modifier = Modifier.padding(8.dp),
                )
            }

            if (state.isLoading) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator(
                    color = Color(0xFF6366F1),
                    strokeWidth = 4.dp,
                )
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
