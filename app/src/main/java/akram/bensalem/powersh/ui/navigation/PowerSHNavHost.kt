package akram.bensalem.powersh.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import timber.log.Timber
import akram.bensalem.powersh.ui.main.screenStates.DetailsScreenState
import akram.bensalem.powersh.ui.main.screenStates.MainListScreenState
import akram.bensalem.powersh.ui.main.screenStates.SettingsScreenState
import akram.bensalem.powersh.ui.main.screens.*
import akram.bensalem.powersh.ui.main.viewModel.DetailsViewModel
import akram.bensalem.powersh.ui.main.viewModel.ListViewModel
import akram.bensalem.powersh.ui.main.viewModel.SettingsViewModel
import akram.bensalem.powersh.utils.scaleInEnterTransition
import akram.bensalem.powersh.utils.scaleInPopEnterTransition
import akram.bensalem.powersh.utils.scaleOutExitTransition
import akram.bensalem.powersh.utils.scaleOutPopExitTransition





@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    AnimatedNavHost(
        navController = navController,
        startDestination = PowerSHScreens.MainListScreen.name
    ) {
        Timber.d("thinkpadNavHost called")
        val thinkpadDetailsScreen = PowerSHScreens.DetailsScreen.name

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
            val thinkpadListState by viewModel.uiState.collectAsState()
            val thinkpadListScreenData =
                thinkpadListState as MainListScreenState.MainListScreen

            Timber.d("thinkpadListScreen NavHost called")

            ThinkpadListScreen(
                modifier = modifier,
                shoeProductList = thinkpadListScreenData.shoeProductList,
                networkLoading = thinkpadListScreenData.networkLoading,
                onSearch = { query ->
                    viewModel
                        .getNewThinkpadListFromDatabase(query)
                },
                onEntryClick = { thinkpad ->
                    navController.navigate(
                        route = "$thinkpadDetailsScreen/${thinkpad.title}"
                    )
                },
                onTabClicked = { tabIndex ->
                   // viewModel.
                } ,
                networkError = thinkpadListScreenData.networkError,
                currentSortOption = thinkpadListScreenData.sortOption,
                onSortOptionClicked = { sort ->
                    viewModel.sortSelected(sort)
                },
                onSettingsClicked = {
                    navController.navigate(
                        route = PowerSHScreens.SettingsScreen.name
                    )
                },
                onAboutClicked = {
                    navController.navigate(
                        route = PowerSHScreens.AboutScreen.name
                    )
                },
                onCheckUpdates = {
                    // TODO: Check updates implementation
                }
            )
        }

        // Details Screen
        composable(
            route = "$thinkpadDetailsScreen/{thinkpad}",
            arguments = listOf(
                navArgument(name = "thinkpad") {
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
            val thinkpadDetail = detailsViewModel.uiState.collectAsState()
            val context = LocalContext.current

            if (thinkpadDetail.value is DetailsScreenState.Detail) {
                val thinkpad =
                    (thinkpadDetail.value as DetailsScreenState.Detail).shoeProduct
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://psref.lenovo.com/syspool/Sys/PDF/ThinkPad/ThinkPad_T450/ThinkPad_T450_Spec.PDF")
                    )
                }
                ThinkpadDetailsScreen(
                    modifier = modifier,
                    shoeProduct = thinkpad,
                    onBackButtonPressed = {
                        navController.popBackStack()
                    },
                    onExternalLinkClicked = {
                        context.startActivity(intent)
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

                ThinkpadSettingsScreen(
                    currentTheme = settingsScreenData.themeOption,
                    currentSortOption = settingsScreenData.sortOption,
                    onThemeOptionClicked = {
                        viewModel.saveThemeSetting(it)
                    },
                    onSortOptionClicked = {
                        viewModel.saveSortOptionSetting(it)
                    },
                    onBackButtonPressed = {
                        navController.popBackStack()
                    }
                )
            }
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
            ThinkpadAboutScreen(
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
