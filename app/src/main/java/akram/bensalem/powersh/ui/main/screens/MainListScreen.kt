package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.*
import akram.bensalem.powersh.ui.main.screenStates.SettingsScreenState
import akram.bensalem.powersh.ui.main.viewModel.SettingsViewModel
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.Theme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.Language
import akram.bensalem.powersh.utils.Sort
import akram.bensalem.powersh.utils.authentification.Authenticate
import akram.bensalem.powersh.utils.localization.Locales
import akram.bensalem.powersh.utils.rememberMapViewWithLifecycle
import android.app.Activity
import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authentication: Authenticate = Authenticate(LocalContext.current as Activity),
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
    orderList: MutableList<OrderItem>,
    networkLoading: Boolean,
    networkError: String,
    isLogged: MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    favouriteProduct: MutableList<ShoeProduct>
){
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 4)


    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )



    val animatedProgress = remember {
        Animatable(initialValue = 1.15f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )



    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth(),
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
            modifier = Modifier
                .fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                mainTopBar(
                    pageState = pageState,
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
            drawerShape = RoundedCornerShape(12.dp).copy(
                topStart = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            ),
            drawerContent = {
                MainDrawer(
                    navController = navController,
                    scope = scope,
                    cartListSize = cartProductList.size,
                    selectedScreen = pageState,
                    scaffoldState = scaffoldState,
                    authentication = authentication,
                    isLogged = isLogged
                )

            },
        ) {


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


                when (pageState.value) {
                    "HOME" -> {
                        LazyColumn(
                            state = listState,
                            modifier = modifier,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Timber.d("MainListScreen Contents called")
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
                                ) {
                                    onTabClicked(it)
                                    coroutineScope.launch {
                                        pagerState.scrollToPage(it, 0f)
                                    }
                                }


                            }
                            itemsIndexed(shoeProductList) { index, item ->
                                if (index % 2 == 0) {
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
                                                    end = if (index + 1 < shoeProductList.size) Dimens.SmallPadding.size else 64.dp,
                                                    start = if (index + 1 < shoeProductList.size) Dimens.ZeroPadding.size else 64.dp,

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
                            //                        item {
                            //                            if (networkError.isNotBlank()) {
                            //                                Text(
                            //                                    text = networkError,
                            //                                    color = MaterialTheme.colors.error,
                            //                                    textAlign = TextAlign.Center
                            //                                )
                            //                            }
                            //                        }

                            item {
                                Spacer(modifier = Modifier.navigationBarsPadding())
                            }
                        }
                    }
                    "CART" -> {
                        cartScreen(
                            navController = navController,
                            cartProduct = cartProductList,
                        )
                    }
                    "FAVOURITE" -> {
                        FavouriteScreen(
                            cartFavourite = favouriteProduct,
                            onEntryClick = onEntryClick
                        )
                    }
                    "CONTACT" -> {
                        contactScreen()
                    }
                    "SETTINGS" -> {
                        SettingsPage()
                    }
                    "ABOUT" -> {
                        AboutScreen()
                    }
                    "ORDERS" -> {
                        orderScreen(orderList = orderList, onPrinted = {}, onEntryClick = {})
                    }
                    else -> {
                        Text(
                            text = pageState.value,
                            color = Color.Black
                        )

                    }
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
fun AboutScreen() {


    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = Modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )


    val alphaAnimatedProgress = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = Unit) {
        alphaAnimatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }


    val alphaAnimatedModifier = Modifier
        .graphicsLayer(
            alpha = alphaAnimatedProgress.value,
        )


    val isMapVisible = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isMapVisible.value) {
        if (!isMapVisible.value) {
            delay(500)
            isMapVisible.value = true
        }
    }

        LazyColumn(
            modifier = animatedModifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(
                    vertical = Dimens.MediumPadding.size,
                    horizontal = Dimens.SmallPadding.size
                )
        ) {
            item{
                Column(alphaAnimatedModifier.fillMaxWidth()) {
                    AboutTopSection(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        appName = LocalStrings.current.powerSh,
                        version = "1.0.0",
                        appLogo = painterResource(id = R.drawable.big_circle_powersh),
                        onCheckUpdatesClicked = {
                            isMapVisible.value = true
                        }
                    )
                }
            }

            item {
                Image(
                    modifier = alphaAnimatedModifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = LocalStrings.current.appLogo
                )
            }

            item {
                Row(
                    modifier = alphaAnimatedModifier
                        .fillMaxWidth()
                        .padding(Dimens.MediumPadding.size)
                ) {
                    Text(
                        LocalStrings.current.projectMadeBy,
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        LocalStrings.current.akramBensalem,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = Dimens.MiniSmallPadding.size)
                    )
                }

            }


            item {

                val localStrings = LocalStrings.current
                val coroutineScope = rememberCoroutineScope()

                val mapView = rememberMapViewWithLifecycle()
                if (isMapVisible.value) {

                    AndroidView(
                        modifier = alphaAnimatedModifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .padding(Dimens.LargePadding.size, Dimens.MediumPadding.size)
                            .background(Color.Transparent, RoundedCornerShape(12.dp))
                            .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.onSurface,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        factory = { mapView }) { mapView ->
                        CoroutineScope(Dispatchers.Main).launch {
                            coroutineScope.launch {
                                val map = mapView.awaitMap()
                                map.uiSettings.isZoomControlsEnabled = true
                                val pickUp = LatLng(36.25682, 2.78190)
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUp, 10f))
                                val markerOptions = MarkerOptions()
                                    .title(localStrings.akramBensalem)
                                    .position(pickUp)
                                map.addMarker(markerOptions)
                            }
                        }
                    }
                }  else {
                    Column(
                        animatedModifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(modifier = animatedModifier.align(Alignment.CenterHorizontally))

                    }
                }
            }

            item{
                Text(
                    text = LocalStrings.current.madeByAkramBensalem,
                    modifier = alphaAnimatedModifier
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
        }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsPage() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val settingsScreenState by viewModel.uiState.collectAsState()
    if (settingsScreenState is SettingsScreenState.Settings) {
        val settingsScreenData =
            settingsScreenState as SettingsScreenState.Settings


        val scope = rememberCoroutineScope()
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )
        val settingsEntryName = rememberSaveable {
            mutableStateOf("")
        }

        ModalBottomSheetLayout(
            sheetContent = {
                SettingEntrySheet(
                    sheetState = sheetState,
                    scope = scope,
                    settingsEntryName = settingsEntryName.value,
                    currentSortOption = settingsScreenData.sortOption,
                    currentLanguageOption = settingsScreenData.languageOption,
                    onSortOptionClicked = {
                        viewModel.saveSortOptionSetting(it)
                    },
                    currentTheme = settingsScreenData.themeOption,
                    onThemeOptionClicked = {
                        scope.launch {
                            sheetState.hide()
                        }
                        viewModel.saveThemeSetting(it)
                    },
                    onLanguageOptionClicked = {
                        viewModel.saveLanguageSetting(it)
                        scope.launch {
                            sheetState.hide()
                        }
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
            },
            sheetState = sheetState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent
        ) {


            LazyColumn(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    val language = when (settingsScreenData.languageOption) {
                        1 -> Language.ARABIC
                        2 -> Language.FRENCH
                        3 -> Language.ENGLISH
                        else -> Language.FOLLOW_SYSTEM
                    }
                    SettingsEntryLanguage(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = LocalStrings.current.languageOption,
                        currentSettingValue =if (language.languageName != Language.FOLLOW_SYSTEM.languageName) language.languageName else LocalStrings.current.followSystemMode,
                        currentSettingIcon = language.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }



                item {
                    val theme = when (settingsScreenData.themeOption) {
                        1 -> Theme.LIGHT_THEME
                        2 -> Theme.DARK_THEME
                        else -> Theme.FOLLOW_SYSTEM
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName = LocalStrings.current.themeOptions,
                        currentSettingValue = when(theme.themeName){
                            Theme.LIGHT_THEME.themeName -> LocalStrings.current.lightTheme
                            Theme.DARK_THEME.themeName -> LocalStrings.current.darkTheme
                            else -> LocalStrings.current.followSystemMode
                        },
                        currentSettingIcon = theme.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
                item {
                    val sort = when (settingsScreenData.sortOption) {
                        1 -> Sort.NEW_RELEASE_FIRST
                        2 -> Sort.OLD_RELEASE_FIRST
                        3 -> Sort.LOW_PRICE_FIRST
                        4 -> Sort.HIGH_PRICE_FIRST
                        else -> Sort.ALPHABETICAL_ASC
                    }
                    SettingsEntry(
                        modifier = Modifier.padding(
                            vertical = Dimens.SmallPadding.size,
                            horizontal = Dimens.MediumPadding.size
                        ),
                        settingsEntryName =LocalStrings.current.sortOptions,
                        currentSettingValue = when(sort.type){
                            Sort.ALPHABETICAL_ASC.type -> LocalStrings.current.alphabeticASC
                            Sort.HIGH_PRICE_FIRST.type -> LocalStrings.current.heightPrice
                            Sort.LOW_PRICE_FIRST.type -> LocalStrings.current.lowPrice
                            Sort.NEW_RELEASE_FIRST.type -> LocalStrings.current.firstRelease
                            Sort.OLD_RELEASE_FIRST.type -> LocalStrings.current.lastRelease
                            else -> LocalStrings.current.alphabeticASC
                        },
                        currentSettingIcon = sort.icon,
                        onSettingsEntryClick = {
                            settingsEntryName.value = it
                            scope.launch {
                                sheetState.show()
                            }
                        }
                    )
                }
            }


        }


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
private fun ListScreenPreview() {
    val scaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
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
            cartProductList = cartProduct,
            networkLoading = false,
            networkError = "",
            favouriteProduct = Constants.ShoesListPreview as MutableList<ShoeProduct>,
            orderList = ArrayList()
        )
    }
}
