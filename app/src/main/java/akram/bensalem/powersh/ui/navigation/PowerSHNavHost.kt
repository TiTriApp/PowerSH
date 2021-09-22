package akram.bensalem.powersh.ui.navigation

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.main.screenStates.DetailsScreenState
import akram.bensalem.powersh.ui.main.screenStates.MainListScreenState
import akram.bensalem.powersh.ui.main.screenStates.SettingsScreenState
import akram.bensalem.powersh.ui.main.screens.*
import akram.bensalem.powersh.ui.main.screens.login.AuthenticationScreen
import akram.bensalem.powersh.ui.main.viewModel.DetailsViewModel
import akram.bensalem.powersh.ui.main.viewModel.ListViewModel
import akram.bensalem.powersh.ui.main.viewModel.SettingsViewModel
import akram.bensalem.powersh.utils.*
import akram.bensalem.powersh.utils.authentification.Authenticate
import akram.bensalem.powersh.utils.localization.Locales
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.akram.bensalem.powersh.ui.screens.onboarding.OnBoardingContent
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalPermissionsApi
@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun PowerSHNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = PowerSHScreens.SplashScreen.name,
    authentication: MutableState<Authenticate> = remember { mutableStateOf(Authenticate(null)) } ,
    cartProduct: MutableList<CardItem>,
    orderList: MutableList<OrderItem>,
    navController: NavHostController,
    pageState: MutableState<String>,
    favouriteProduct: MutableList<ShoeProduct>,
    isLogged: MutableState<Boolean>,
) {





    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // Checkout Screen
        composable(
            route = PowerSHScreens.SplashScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {




            Box(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
            ){

                LaunchedEffect(key1 = null){
                 delay(300)
                    navController.navigate(PowerSHScreens.OnBoardingScreen.name){
                            popUpTo(PowerSHScreens.MainListScreen.name)

                    }

                }
                Image(
                    modifier = Modifier
                        .size(160.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id =akram.bensalem.powersh.R.drawable.big_circle_powersh),
                    contentDescription = LocalStrings.current.appLogo,
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                    text = "Made with ❤ by Akram Bensalem",
                    color = Color.White
                )
            }
        }


        // Checkout Screen
        composable(
            route = PowerSHScreens.CheckoutScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {

            CheckoutScreen(
                cartProduct,
                onConfirmClicked = {
                    orderList.add(
                        it
                    )
                    pageState.value = "HOME"
                    navController.navigate(PowerSHScreens.MainListScreen.name)
                    cartProduct.clear()
                },
                onBackPressClicked = {
                    navController.popBackStack()
                }
            )


        }


        // First interaction Screen
        composable(
            route = PowerSHScreens.OnBoardingScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {


            OnBoardingContent(
                onActionClicked = {
                    navController.navigate(
                        route = PowerSHScreens.MainListScreen.name
                    )
                }
            )


        }


// Login  Screen
        composable(
            route = PowerSHScreens.AuthentificationScree.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {

            val tabState = remember {
                mutableStateOf(0)
            }

            BackHandler {
                if (tabState.value == 0) {
                    navController.navigate(PowerSHScreens.MainListScreen.name)
                } else {
                    tabState.value = 0
                }
            }

            AuthenticationScreen(
                authentication = authentication,
                onBackButtonPressed = {
                    navController.popBackStack()
                }
            ) {
                navController.navigate(PowerSHScreens.ProfileScreen.name) {
                    popUpTo(PowerSHScreens.MainListScreen.name)
                }
                isLogged.value = true
            }

        }


        val detailsScreen = PowerSHScreens.DetailsScreen.name

        // Main List Screen
        composable(
            route = PowerSHScreens.MainListScreen.name,

            // Transition animations
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            // popEnter and popExit default to enterTransition & exitTransition respectively
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            val viewModel: ListViewModel = hiltViewModel()
         //   val listState by viewModel.uiState.collectAsState()
         /*   val shoesListScreenData =
                listState as MainListScreenState.MainListScreen*/

            val tabIndex = remember {
                mutableStateOf(0)
            }

            val scaffoldState = rememberScaffoldState(
                drawerState = rememberDrawerState(DrawerValue.Closed),
            )
            val scope = rememberCoroutineScope()


            BackHandler {


                if (pageState.value == "HOME") {
                    if (scaffoldState.drawerState.isOpen) {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                } else {
                    pageState.value = "HOME"
                }


            }




            MainListScreen(
                modifier = modifier,
                navController = navController,
           //     shoeProductList = shoesListScreenData.shoeProductList,
                shoeProductList = Constants.ShoesListPreview,
                cartProductList = cartProduct,
                orderList = orderList,
                favouriteProduct = favouriteProduct,
                networkLoading = false,
                pageState = pageState,
                scaffoldState = scaffoldState,
                isLogged = isLogged,
                onSearch = { query ->
                   /* viewModel
                        .getNewShoesListFromDatabase(query, index = tabIndex.value)*/
                },
                onEntryClick = { shoe ->
                  /*  navController.navigate(
                        route = "$detailsScreen/${shoe.title}"
                    )*/
                },
                onTabClicked = { index ->
                    /*tabIndex.value = index
                    viewModel
                        .getNewShoesListFromDatabase("", index = index)*/
                },
                networkError = "shoesListScreenData.networkError",
                currentSortOption = 0,
                onSortOptionClicked = { sort ->
                  //  viewModel.sortSelected(sort, tabIndex.value)
                },
                onSettingsClicked = {
                  /*  navController.navigate(
                        route = PowerSHScreens.SettingsScreen.name
                    )*/
                },
                onAboutClicked = {
                  /*  navController.navigate(
                        route = PowerSHScreens.AboutScreen.name
                    )*/
                },
                onCheckUpdates = {
                   // viewModel.refreshShoesList()
                }
            )
        }

        // Details Screen
        composable(
            route = "$detailsScreen/{shoe}",
            arguments = listOf(
                navArgument(name = "shoe") {
                    type = NavType.StringType
                }
            ),
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {

            val detailsViewModel: DetailsViewModel = hiltViewModel()
            val shoeDetail = detailsViewModel.uiState.collectAsState()

            if (shoeDetail.value is DetailsScreenState.Detail) {
                val shoe =
                    (shoeDetail.value as DetailsScreenState.Detail).shoeProduct


                val favourite = remember {
                    mutableStateOf(favouriteProduct.contains(shoe))
                }

                DetailsScreen(
                    modifier = modifier,
                    shoeProduct = shoe,
                    cartProduct = cartProduct,
                    favourite = favourite,
                    onBackButtonPressed = {
                        navController.popBackStack()
                    },
                    onFavouriteClick = {

                        if (favourite.value) {
                            favouriteProduct.remove(shoe)
                        } else {
                            favouriteProduct.add(shoe)
                        }

                        favourite.value = !favourite.value
                    },
                    onNavigateToCartScreen = {
                        pageState.value = "CART"
                        navController.popBackStack()
                    }
                )
            }
        }


        // Settings Screen
        composable(
            route = PowerSHScreens.SettingsScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            val viewModel: SettingsViewModel = hiltViewModel()
            val settingsScreenState by viewModel.uiState.collectAsState()
            if (settingsScreenState is SettingsScreenState.Settings) {
                val settingsScreenData =
                    settingsScreenState as SettingsScreenState.Settings

                SettingsScreen(
                    currentTheme = settingsScreenData.themeOption,
                    currentSortOption = settingsScreenData.sortOption,
                    currentLanguageOption =  settingsScreenData.languageOption,
                    onThemeOptionClicked = {
                        viewModel.saveThemeSetting(it)
                    },
                    onSortOptionClicked = {
                        viewModel.saveSortOptionSetting(it)
                    },
                    onBackButtonPressed = {
                        navController.popBackStack()
                    },
                    onLanguageOptionClicked = {
                        viewModel.saveLanguageSetting(it)

                        when(it) {
                            1 -> lyricist.languageTag = Locales.AR
                            2 -> lyricist.languageTag = Locales.FR
                            3 -> lyricist.languageTag = Locales.EN
                            else -> {
                                when(Locale.getDefault().language){
                                    "en" -> lyricist.languageTag = Locales.EN
                                    "fr" -> lyricist.languageTag =Locales.FR
                                    "ar" -> lyricist.languageTag =Locales.AR
                                    else -> lyricist.languageTag =Locales.EN
                                }
                             }
                        }



                    }
                )
            }
        }


        //Profile Screen
        composable(
            route = PowerSHScreens.ProfileScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            val isInviteSent = remember {
                mutableStateOf(false)
            }

            val context = LocalContext.current

            profileScreen(
                authentication = authentication,
                onBackButtonPressed = {
                    navController.popBackStack()
                },
                onLogOutClick = {
                    authentication.value.signOut(isLogged)
                    navController.navigate(PowerSHScreens.AuthentificationScree.name) {
                        popUpTo(PowerSHScreens.MainListScreen.name)
                    }
                },
                onSwitchChange = {
                    val msg = if (it) {
                        "The notification have been activated"
                    } else {
                        "The notification have been deactivated"
                    }

                    Toast.makeText(
                        context,
                        msg,
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onViewHistoryClicked = {
                    Toast.makeText(
                        context,
                        "You don't have any history to view!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                onInviteClicked = {
                    sendEmail(
                        context = context,
                        recipient = "powersshoes2@gmail.com",
                        subject = "PowerSH",
                        message = "Hi, I'm invite you to use this app",
                        title = "Invite your friends",
                        isMessageSent = isInviteSent
                    )
                }
            )

        }


        // About Screen
        composable(
            route = PowerSHScreens.AboutScreen.name,
            enterTransition = { _, _ ->
                scaleInEnterTransition()
            },
            exitTransition = { _, _ ->
                scaleOutExitTransition()
            },
            popEnterTransition = { _, _ ->
                scaleInPopEnterTransition()
            },
            popExitTransition = { _, _ ->
                scaleOutPopExitTransition()
            }
        ) {
            AboutScreen(
                onCheckUpdates = {
                    // TODO: Check updates implementation
                },
                onBackButtonPressed = {
                    navController.popBackStack()
                }
            )

        }
    }

}
