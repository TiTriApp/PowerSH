package akram.bensalem.powersh.ui.main.screens

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.components.*
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants

@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkpadListScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onEntryClick: (ShoeProduct) -> Unit = { },
    onSearch: (String) -> Unit = { },
    onTabClicked:(Int) -> Unit,
    onSortOptionClicked: (Int) -> Unit = { },
    onSettingsClicked: () -> Unit = { },
    onAboutClicked: () -> Unit = { },
    onCheckUpdates: () -> Unit = { },
    currentSortOption: Int,
    shoeProductList: List<ShoeProduct>,
    networkLoading: Boolean,
    networkError: String
) {


    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 4)


    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )


    val navController = rememberNavController()
    val pageState = remember { mutableStateOf("HOME") }




    val scaffoldState = rememberScaffoldState(
        drawerState= rememberDrawerState(DrawerValue.Closed),
    )

    var refreshing by remember { mutableStateOf(false) }



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
                    scaffoldState = scaffoldState
                )

            },
        ){


            var filterType = remember {
                mutableStateOf(1000)
            }
            val swipeRefreshState = rememberSwipeRefreshState(refreshing)
            LaunchedEffect(refreshing) {
                if (refreshing) {
                    delay(1200)
                    refreshing = false
                }
            }

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    refreshing = true
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
                            vertical = Dimens.SmallPadding.size
                        )
                    )

                    TabsPanel(
                        pagerState = pagerState
                    ){
                        onSearch("$it")
                        coroutineScope.launch {
                            pagerState.scrollToPage(it, 0f)
                        }
                    }


                }
                /*
                item {
                    HorizontalPager(state = pagerState) { page ->
                        when (page) {

                            0 -> {
                                filterType.value = 250
                            }
                            1 -> {
                                filterType.value = 500
                            }
                            2 -> {
                                filterType.value = 1000
                            }
                            3 -> {
                                filterType.value = 5000
                            }

                        }

                    }
                }

                 */
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
                        refreshing = true
                    }else{
                        refreshing = false
                    }
                }
                item {
                    if (networkError.isNotBlank()) {
                        Text(
                            text = networkError,
                            color = MaterialTheme.colors.error,
                            textAlign = TextAlign.Center
                        )
                      refreshing = false
                    }
                }

                item {
                    Spacer(modifier = Modifier.navigationBarsPadding())
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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun ThinkpadListScreenPreview() {
    PowerSHTheme {
        ThinkpadListScreen(
            shoeProductList = Constants.ShoesListPreview,
            networkLoading = false,
            networkError = "",
            currentSortOption = 0,
            onTabClicked = {},
        )
    }
}
