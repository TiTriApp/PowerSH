package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import timber.log.Timber
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.components.*
import akram.bensalem.powersh.ui.main.screenStates.SettingsScreenState
import akram.bensalem.powersh.ui.main.viewModel.SettingsViewModel
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.authentification.Authentifier
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    listState: LazyListState = rememberLazyListState(),
    pageState: MutableState<String>,
    scaffoldState: ScaffoldState,
    onEntryClick: (ShoeProduct) -> Unit = { },
    onSearch: (String) -> Unit = { },
    onTabClicked: (Int) -> Unit,
    onSortOptionClicked: (Int) -> Unit = { },
    onSettingsClicked: () -> Unit = { },
    onAboutClicked: () -> Unit = { },
    onCheckUpdates: () -> Unit = { },
    currentSortOption: Int,
    shoeProductList: List<ShoeProduct>,
    cartProductList: MutableList<CardItem>,
    networkLoading: Boolean,
    networkError: String,
    favouriteProduct: MutableList<ShoeProduct>
) {


    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 4)


    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )


    val activity = LocalContext.current as Activity

    var authentification : Authentifier = Authentifier(activity)



    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        sheetContent = {
            ModalBottomSheet(
                sheetState = sheetState,
                scope = scope,
                onSortOptionClicked = {
                    onSortOptionClicked(it)
                },
                currentSortOption = currentSortOption,
                onSettingsClicked = onSettingsClicked,
                onCheckUpdates = onCheckUpdates,
                onAboutClicked = onAboutClicked
            )
        },
        sheetState = sheetState,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                mainTopBar(
                    onOpenMenu = {
                        scope.launch {
                            if (scaffoldState.drawerState.isOpen) {
                                scaffoldState.drawerState.close()
                            } else {
                                scaffoldState.drawerState.open()
                            }
                        }
                    }
                )
            },
            drawerContent = {
                mainDrawer(
                    navController = navController,
                    scope = scope,
                    selectedScreen = pageState,
                    scaffoldState = scaffoldState,
                    authentification = authentification
                )

            },
        ){



            val swipeRefreshState = rememberSwipeRefreshState(networkLoading)

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    onCheckUpdates()
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        contentColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.background,
                        shape = CircleShape,
                    )
                }
            ) {


                if (
                    pageState.value.equals("HOME")
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Timber.d("thinkpadListScreen Contents called")
                        item {
                            CustomSearchBar(
                                focusManager = focusManager,
                                onSearch = {
                                    onSearch(it)
                                },
                                onDismissSearchClicked = {
                                    onSearch("")
                                },
                                onOptionsClicked = {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                },
                                modifier = Modifier.padding(
                                    vertical = Dimens.SmallPadding.size,
                                    horizontal = Dimens.SmallPadding.size
                                )
                            )

                            TabsPanel(
                                pagerState = pagerState
                            ){
                                onTabClicked(it)
                                coroutineScope.launch {
                                    pagerState.scrollToPage(it, 0f)
                                }
                            }


                        }
                        itemsIndexed(shoeProductList){ index, item ->
                            if (index % 2 == 0 ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = Dimens.UpperMediumPadding.size,
                                            vertical = Dimens.SmallPadding.size,
                                        )
                                ) {

                                    ProductShoesEntry(
                                        shoeProduct = item,
                                        onEntryClick = { onEntryClick(item) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(
                                                end = if (index + 1 < shoeProductList.size) Dimens.SmallPadding.size else 0.dp,
                                            )
                                    )
                                    if (index + 1 < shoeProductList.size) {
                                        ProductShoesEntry(
                                            shoeProduct = shoeProductList[index + 1],
                                            onEntryClick = { onEntryClick(shoeProductList[index + 1]) },
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(
                                                    start = Dimens.SmallPadding.size,
                                                )
                                        )
                                    }


                                }

                            }
                        }

                        item {
                            if (networkLoading) {
                                CircularProgressIndicator()
                            }
                        }
                        item {
                            if (networkError.isNotBlank()) {
                                Text(
                                    text = networkError,
                                    color = MaterialTheme.colors.error,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.navigationBarsPadding())
                        }
                    }
                } else if (
                    pageState.value.equals("CART")
                ) {
                    cartScreen(
                        navController = navController,
                        cartProduct= cartProductList,
                    )
                } else if (
                    pageState.value.equals("FAVOURITE")
                ){
                   favouriteScreen(cartFavourite = favouriteProduct, onEntryClick = onEntryClick)
                }else if (
                    pageState.value.equals("CONTACT")
                ){
                    contactScreen()
                } else if (
                    pageState.value.equals("SETTINGS")
                ){
                    SettingsPage(pageState = pageState)
                } else if (
                    pageState.value.equals("ABOUT")
                ){
                    aboutScreen()
                } else {
                    Text(
                        text = pageState.value,
                        color = Color.Black
                    )

                }




            }
            ScrollToTopButton(
                listState = listState,
                modifier = Modifier.fillMaxWidth(),
                scope = scope
            )
        }
    }


}



@Composable
fun aboutScreen(){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                AboutTopSection(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    appName = stringResource(id = R.string.app_name),
                    version = "1.0.0",
                    appLogo = painterResource(id = R.drawable.big_circle_powersh),
                    onCheckUpdatesClicked = {}
                )
            }

        },
        bottomBar = {
            Text(
                text = stringResource(id = R.string.made_with_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.SmallPadding.size)
                    .navigationBarsPadding(),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onSurface
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(vertical = Dimens.MediumPadding.size, horizontal = Dimens.SmallPadding.size)) {
            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = stringResource(id = R.string.app_logo)
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.MediumPadding.size)
                ) {
                    Text(
                        "This project is made by:",
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        "- Akram Bensalem \n- Arbaoui Slimane\n- BELMILOUD ILIES DHIAEDDINE\n- Abdelkader YAHIAOUI\n- HADJ SADOK MOHAMMED NAZIM\n- ABDELLATIF ABDERAOUF\n- Merouan Boughedda",
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = Dimens.MiniSmallPadding.size)
                    )
                }

            }

        }

    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsPage(
    pageState: MutableState<String>,

    ){
    val viewModel: SettingsViewModel = hiltViewModel()
    val settingsScreenState by viewModel.uiState.collectAsState()
    if (settingsScreenState is SettingsScreenState.Settings) {
        val settingsScreenData =
            settingsScreenState as SettingsScreenState.Settings

        SettingsScreen(
            currentTheme = settingsScreenData.themeOption,
            currentSortOption = settingsScreenData.sortOption,
            onThemeOptionClicked = {
                viewModel.saveThemeSetting(it)
            },
            onSortOptionClicked = {
                viewModel.saveSortOptionSetting(it)
            },
            onBackButtonPressed = {
                pageState.value = "HOME"
            }
        )
    }
}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun ThinkpadListScreenPreview() {
    val scaffoldState = rememberScaffoldState(
        drawerState= rememberDrawerState(DrawerValue.Closed),
    )
    val pageState = remember { mutableStateOf("HOME") }

    val cartProduct = remember { Constants.cartList }




    PowerSHTheme {
        MainListScreen(
            navController = rememberNavController(),
            pageState = pageState,
            scaffoldState = scaffoldState,
            onTabClicked = {},
            currentSortOption = 0,
            shoeProductList = Constants.ShoesListPreview,
            cartProductList =cartProduct,
            networkLoading = false,
            networkError = "",
            favouriteProduct = Constants.ShoesListPreview as MutableList<ShoeProduct>,
        )
    }
}
