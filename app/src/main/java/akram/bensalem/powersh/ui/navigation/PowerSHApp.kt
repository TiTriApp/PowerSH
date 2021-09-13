package akram.bensalem.powersh.ui.navigation

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun PowerSHApp(
    themeValue: Int = 0,
    cartProduct: MutableList<CardItem>,
    favouriteProduct: SnapshotStateList<ShoeProduct>,
    ) {

   var pageState = remember {
       mutableStateOf("HOME")
   }

    ProvideWindowInsets {
        PowerSHTheme(theme = themeValue) {
            val navController = rememberAnimatedNavController()
            PowerSHNavHost(
                modifier = Modifier,
                navController = navController,
                cartProduct = cartProduct,
                favouriteProduct = favouriteProduct,
                pageState = pageState
            )
        }
    }
}