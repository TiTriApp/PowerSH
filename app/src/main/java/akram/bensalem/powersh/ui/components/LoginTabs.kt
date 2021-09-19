package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.utils.localization.Strings
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

data class LoginState(
    var state: Screen = Screen.LOGIN,
                      ) {

    enum class Screen(
        val title: String = "Tab",
    ) {
        LOGIN(title = "Login"),
        SIGN_UP(title = "Sign Up"),
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun loginTabs(
    modifier: Modifier,
    pagerState: PagerState,
    onPageSelected: (Int) -> Unit
) {

    val tabs = LoginState.Screen.values()

    TabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = CardCoverPink,
        divider = {
            TabRowDefaults.Divider(
                color = MaterialTheme.colors.surface
            )
        },
        tabs = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = if (tab.title ==LoginState.Screen.LOGIN.title) LocalStrings.current.login else LocalStrings.current.signUp,
                            color = if (pagerState.currentPage == index) Color.White else CardCoverPink,
                        )
                    },
                    selected = index == pagerState.currentPage,
                    selectedContentColor = Color.White,
                    onClick = {
                        onPageSelected(index)
                    }
                )
            }
        }
    )
}
