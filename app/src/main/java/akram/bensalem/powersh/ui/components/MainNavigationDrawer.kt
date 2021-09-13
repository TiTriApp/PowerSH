package akram.bensalem.powersh.ui.components


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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.main.screens.PowerSHScreens
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authentifier
import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.insets.navigationBarsPadding


sealed class DrawerScreens(val title: String) {
    object Home : DrawerScreens("Home")
    object Account : DrawerScreens("Cart")
    object Help : DrawerScreens( "Favourite")
}

private val screens = listOf(
    DrawerScreens.Home,
    DrawerScreens.Account,
    DrawerScreens.Help
)




@ExperimentalMaterialApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun mainDrawer(
    navController: NavController,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    selectedScreen: MutableState<String>,
    scaffoldState: ScaffoldState,
    authentification: Authentifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(start =0.dp, top = 0.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(radius = 250.dp)
                ) {
                    if (authentification.user != null) {
                        navController.navigate(PowerSHScreens.ProfileScreen.name)
                    }   else {
                        navController.navigate(PowerSHScreens.AuthentificationScree.name)
                    }
                }
                .padding(start = 16.dp , top = 48.dp , bottom = Dimens.MediumPadding.size)
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
                    text =if (authentification.user != null) authentification.userName else "Sign In",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.onBackground,

                    )
                Text(
                    text = if (authentification.user != null) authentification.userEmail else "Or Creat an account",
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
            modifier = Modifier.padding(start = 16.dp , top = 48.dp)
        ) {

            DrawerRow(
                title =  "Home",
                icon = Icons.Outlined.Home,
                id =  "HOME",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )



            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "Cart",
                icon = Icons.Outlined.ShoppingCart,
                id =  "CART",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )



            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "Favourite",
                icon = Icons.Outlined.FavoriteBorder,
                id =  "FAVOURITE",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )

            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "Orders",
                icon = Icons.Outlined.LocalShipping,
                id =  "ORDERS",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "Settings",
                icon = Icons.Outlined.Settings,
                id =  "SETTINGS",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "About Us",
                icon = Icons.Outlined.Info,
                id =  "ABOUT",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )


            Spacer(modifier = Modifier.padding(8.dp))
            DrawerRow(
                title =  "Contact Us",
                icon = Icons.Outlined.MailOutline,
                id =  "CONTACT",
                scope = scope,
                selectedScreen = selectedScreen,
                scaffoldState = scaffoldState,
            )

            Spacer(modifier = Modifier.weight(1f))

            if (authentification.user != null) {
                LogOutDrawerRow(
                    title = "Log Out",
                    icon = Icons.Outlined.DoorBack,
                    id = "LOG OUT",
                    scope = scope,
                    selectedScreen = selectedScreen,
                    scaffoldState = scaffoldState,
                    authentification = authentification
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
){
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
            modifier = Modifier.size(36.dp),
            tint =if (selectedScreen.value.equals(id.uppercase())) MaterialTheme.colors.primary  else MaterialTheme.colors.onBackground,
        )
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(CenterVertically)
                .fillMaxWidth(),
            color =if (selectedScreen.value.equals(id.uppercase())) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,

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
    authentification: Authentifier,
    ){
    Row(modifier = Modifier
        .clickable {
            scope.launch {
                scaffoldState.drawerState.close()
                authentification.signOut()
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
            tint =if (selectedScreen.value.equals(id.uppercase())) MaterialTheme.colors.primary  else MaterialTheme.colors.onBackground,
        )
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .align(CenterVertically)
                .fillMaxWidth(),
            color =if (selectedScreen.value.equals(id.uppercase())) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground)
    }

}






@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun drawerPreview() {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(
        drawerState= rememberDrawerState(DrawerValue.Closed),
    )

    val selectedItem = remember { mutableStateOf("HOME") }

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    PowerSHTheme {
        mainDrawer(
            navController,
            scope = scope,
            selectedScreen = selectedItem,
            scaffoldState = scaffoldState,
            authentification = Authentifier(activity)
        )
    }
}