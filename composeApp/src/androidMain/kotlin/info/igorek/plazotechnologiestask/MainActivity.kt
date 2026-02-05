package info.igorek.plazotechnologiestask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import info.igorek.plazotechnologiestask.root.RootComponent
import info.igorek.plazotechnologiestask.root.RootUi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            RootUi(RootComponent(defaultComponentContext()))
        }
    }
}
