package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.localization.Locales
import akram.bensalem.powersh.utils.localization.Strings
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding


@Composable
fun mainTopBar(
    pageState: MutableState<String> = remember {
        mutableStateOf( "PowerSH")
    },
    onOpenMenu: () -> Unit = {},
) {


    TopAppBar(
        title = {
            Column(Modifier
                .statusBarsPadding()) {
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colors.onBackground,
                    text = getScreenTitle(state = pageState.value, localString = LocalStrings.current)
                )
                Spacer(modifier = Modifier.fillMaxWidth().height(16.dp))
            }

        },
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            Column(Modifier.statusBarsPadding()) {
                IconButton(onClick = {
                    onOpenMenu()
                }) {
                    Icon(
                        modifier = Modifier
                            .graphicsLayer {
                                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                            },
                        tint = MaterialTheme.colors.onBackground,
                        imageVector = Icons.Filled.Menu,
                        contentDescription = LocalStrings.current.openMenu
                    )
                }
            }
        },
        actions = {},
        elevation = 0.dp,
    )
}


fun getScreenTitle(state: String, localString: Strings): String {
    return when (state) {
        "HOME" -> {
            localString.title
        }
        "CART" -> {
            localString.cart
        }
        "FAVOURITE" -> {
            localString.favourite
        }
        "CONTACT" -> {
            localString.contactUs
        }
        "SETTINGS" -> {
            localString.settings
        }
        "ABOUT" -> {
            localString.aboutUs
        }
        "ORDERS" -> {
            localString.orders
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