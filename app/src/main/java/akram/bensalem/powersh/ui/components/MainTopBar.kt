package akram.bensalem.powersh.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import akram.bensalem.powersh.ui.theme.PowerSHTheme

@Composable
fun mainTopBar(
    onOpenMenu:  () -> Unit ={},
) {
    TopAppBar(
        title = {
            Text(
                color = MaterialTheme.colors.onBackground,
                text = "PowerSH"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = {
                onOpenMenu()
            }) {
                Icon(
                    tint = MaterialTheme.colors.onBackground,
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Menu"
                )
            }
        },
        actions = {},
        elevation = 0.dp,
    )
}


@Preview
@Composable
fun topBarPreview() {

    PowerSHTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            mainTopBar()

        }


    }

}