package akram.bensalem.powersh.ui.components


import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.main.screens.PowerSHScreens
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authenticate
import akram.bensalem.powersh.utils.localization.Locales
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainDrawer(
    navController: NavController,
    modifier: Modifier = Modifier,
    cartListSize : Int = 0,
    scope: CoroutineScope,
    selectedScreen: MutableState<String>,
    scaffoldState: ScaffoldState,
    authentication: Authenticate,
    isLogged : MutableState<Boolean>
) {
    Column(
        modifier
            .fillMaxSize()
            .statusBarsPadding()

    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    if (isLogged.value) {
                        navController.navigate(PowerSHScreens.ProfileScreen.name)
                    } else {
                        navController.navigate(PowerSHScreens.AuthentificationScree.name)
                    }
                }
                .padding(start = 16.dp, top = 48.dp, bottom = Dimens.MediumPadding.size)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_user),
                contentDescription = "App icon",
                modifier = Modifier
                    .size(108.dp)
                    .padding(start = 16.dp)

            )

            Column(
                modifier = Modifier
                    .align(CenterVertically)

            ) {
                Text(
                    text = if (authentication.user != null) authentication.userName(LocalStrings.current) else LocalStrings.current.signIn,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground,

                    )
                Text(
                    text = if (authentication.user != null) authentication.userEmail(LocalStrings.current) else LocalStrings.current.creatAccount,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground,
                )
            }


        }




        Column(
            modifier = Modifier.padding(start = 16.dp, top = 48.dp)
        ) {

            DrawerRow(
                title = LocalStrings.current.home,
                icon = Icons.Outlined.Home,
                id = "HOME",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )



            Spacer(modifier = Modifier.padding(8.dp))
            BadgeDrawerRow(
                title = LocalStrings.current.cart,
                icon = Icons.Outlined.ShoppingCart,
                cartListSize = cartListSize,
                id = "CART",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )



            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title = LocalStrings.current.favourite,
                icon = Icons.Outlined.FavoriteBorder,
                id = "FAVOURITE",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )

            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title = LocalStrings.current.orders,
                icon = Icons.Outlined.LocalShipping,
                id = "ORDERS",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title = LocalStrings.current.settings,
                icon = Icons.Outlined.Settings,
                id = "SETTINGS",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title = LocalStrings.current.aboutUs,
                icon = Icons.Outlined.Info,
                id = "ABOUT",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )


            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =LocalStrings.current.contactUs,
                icon = Icons.Outlined.MailOutline,
                id = "CONTACT",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isLogged.value) {
                LogOutDrawerRow(
                    title =LocalStrings.current.logOut,
                    icon = Icons.Outlined.DoorBack,
                    id = "LOG OUT",
                    scope = scope,
                    selectedScreen = selectedScreen,
                    scaffoldState = scaffoldState,
                    authentication = authentication,
                    isLogged = isLogged
                )
                Spacer(modifier = Modifier.padding(8.dp))
            }
            Spacer(modifier = Modifier.navigationBarsPadding())


        }
    }
}


@ExperimentalMaterialApi
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun DrawerRow(
    title: String,
    icon: ImageVector,
    id: String,
    scope: CoroutineScope,
    selectedScreen: MutableState<String>,
    scaffoldState: ScaffoldState,
) {
    Row(modifier = Modifier
        .clickable {
            scope.launch {
                scaffoldState.drawerState.close()
                selectedScreen.value = id.uppercase()
            }
        }
        .fillMaxWidth()
        .padding(0.dp)
        .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {


            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(36.dp)
                                            .graphicsLayer {
                                                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                            },
                tint = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
            )


        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(CenterVertically)
                .fillMaxWidth(),
            color = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,

            )
    }

}



@ExperimentalMaterialApi
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun BadgeDrawerRow(
    title: String,
    cartListSize : Int = 0,
    icon: ImageVector,
    id: String,
    scope: CoroutineScope,
    selectedScreen: MutableState<String>,
    scaffoldState: ScaffoldState,
) {
    Row(modifier = Modifier
        .clickable {
            scope.launch {
                scaffoldState.drawerState.close()
                selectedScreen.value = id.uppercase()
            }
        }
        .fillMaxWidth()
        .padding(0.dp)
        .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {

        BadgedBox(badge = {
            if (cartListSize > 0) Badge { Text(cartListSize.toString()) }
        }) {
            Icon(
                icon,
                contentDescription = title,
                modifier = Modifier.size(36.dp),
                tint = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
            )
        }


        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(CenterVertically)
                .fillMaxWidth(),
            color = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,

            )
    }
}




@ExperimentalMaterialApi
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun LogOutDrawerRow(
    title: String,
    icon: ImageVector,
    id: String,
    scope: CoroutineScope,
    selectedScreen: MutableState<String>,
    scaffoldState: ScaffoldState,
    authentication: Authenticate,
    isLogged: MutableState<Boolean>,
    onClicked : () -> Unit = {}
) {
    Row(modifier = Modifier
        .clickable {
            scope.launch {
                scaffoldState.drawerState.close()
                authentication.signOut(isLogged)
            }
        }
        .fillMaxWidth()
        .padding(0.dp)
        .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Icon(
            icon,
            contentDescription = title,
            modifier = Modifier.size(36.dp),
            tint = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
        )
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(CenterVertically)
                .fillMaxWidth(),
            color = if (selectedScreen.value == id.uppercase()) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
        )
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun DrawerPreview() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
    )

    val selectedItem = remember { mutableStateOf("HOME") }

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    val isLogged = remember {
        mutableStateOf(false)
    }

    PowerSHTheme {
        MainDrawer(
            navController,
            scope = scope,
            isLogged = isLogged,
            selectedScreen = selectedItem,
            scaffoldState = scaffoldState,
            authentication = Authenticate(
                activity
            )
        )
    }
}