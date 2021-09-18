package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.ui.theme.PowerSHTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun mainTopBar(
    pageState: MutableState<String> = remember {
        mutableStateOf("PowerSH")
    },
    onOpenMenu: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                color = MaterialTheme.colors.onBackground,
                text = getScreenTitle(state = pageState.value)
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


fun getScreenTitle(state: String): String {
    return when (state) {
        "HOME" -> {
            "PowerSH"
        }
        "CART" -> {
            "Cart"
        }
        "FAVOURITE" -> {
            "Favourites"
        }
        "CONTACT" -> {
            "Contact Us"
        }
        "SETTINGS" -> {
            "Settings"
        }
        "ABOUT" -> {
            "About Us"
        }
        "ORDERS" -> {
            "Orders"
        }
        else -> {
            "PowerSH"
        }
    }
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