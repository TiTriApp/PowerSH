package akram.bensalem.powersh.ui.components.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBarCheckout(
    step : MutableState<Int> = remember {
        mutableStateOf(0)
    },
    onBackPress : () -> Unit = {}
){
    Column(Modifier.background(MaterialTheme.colors.surface)) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.surface,
            title = {
                Text(
                    color = MaterialTheme.colors.onBackground,
                    text = "Checkout"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        tint = MaterialTheme.colors.onBackground,
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Back to Cart Screen"
                    )
                }
            },
            actions = {},
            elevation = 0.dp,
        )
        StepsWizard(step)
    }
}