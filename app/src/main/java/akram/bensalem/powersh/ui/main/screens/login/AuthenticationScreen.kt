package akram.bensalem.powersh.ui.main.screens.login

import akram.bensalem.powersh.ui.components.authentication.ForgetPasswordBottomSheet
import akram.bensalem.powersh.ui.components.authentication.MainCard
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authenticate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    authentication: MutableState<Authenticate> = remember { mutableStateOf(Authenticate(null)) },
    listState: LazyListState = rememberLazyListState(),
    onBackButtonPressed: () -> Unit = { },
    onLogged: () -> Unit = {},
) {

    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val pagerState = rememberPagerState(pageCount = 2)
    val scope = rememberCoroutineScope()


    val toolbarHeight = 160.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }





    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth(),
        sheetContent = {
            ForgetPasswordBottomSheet(
                authentication = authentication
            )
        },
        sheetState = bottomSheetScaffoldState,
        sheetElevation = 0.dp,
        sheetBackgroundColor = Color.Transparent
    ) {

        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                MainCard(
                    toolbarHeight = toolbarHeight,
                    pagerState = pagerState,
                    toolbarOffset = toolbarOffsetHeightPx.value,
                    onBackButtonPressed = onBackButtonPressed,
                    onPageSelected = {
                        scope.launch {
                            pagerState.animateScrollToPage(it, 0f, skipPages = false)

                        }
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .nestedScroll(nestedScrollConnection),
                state = listState,
                horizontalAlignment = CenterHorizontally
            ) {

                item {
                    HorizontalPager(
                        state = pagerState,
                        itemSpacing = 8.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->

                        if (page == 0) {
                            LoginScreen(
                                bottomSheetScaffoldState = bottomSheetScaffoldState,
                                modifier = Modifier,
                                authentication = authentication,
                                onLogin = onLogged
                            )
                        } else {
                            SignUpScreen(
                                modifier = Modifier,
                                authentication = authentication,
                                onBackToMainScreen = onBackButtonPressed,
                            )
                        }
                    }
                }
            }
        }
    }


}


@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun LoginPage1() {
    PowerSHTheme {
        AuthenticationScreen()
    }
}