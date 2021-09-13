package akram.bensalem.powersh.ui.navigation

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.main.screens.PowerSHScreens
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.authentification.Authentifier
import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun PowerSHApp(
    themeValue: Int = 0,
    cartProduct: MutableList<CardItem>,
    favouriteProduct: SnapshotStateList<ShoeProduct>,
    orderList : MutableList<OrderItem>,
    ) {

   var pageState = remember {
       mutableStateOf("HOME")
   }

     val activity = LocalContext.current as Activity
     var authentification : Authentifier = Authentifier(activity)


    ProvideWindowInsets {
        PowerSHTheme(theme = themeValue) {
            val navController = rememberAnimatedNavController()
            PowerSHNavHost(
                modifier = Modifier,
                startDestination = PowerSHScreens.MainListScreen.name ,
                authentification =authentification,
                navController = navController,
                cartProduct = cartProduct,
                favouriteProduct = favouriteProduct,
                orderList = orderList,
                pageState = pageState
            )
        }
    }
}