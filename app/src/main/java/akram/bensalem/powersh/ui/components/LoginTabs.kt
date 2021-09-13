package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.ui.theme.CardCoverPink
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset

data class LoginState(var state: Screen = Screen.LOGIN) {

    enum class Screen(
        val title: String = "Tab"
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
    onPageSelected : (Int) -> Unit
) {

    val tabs = LoginState.Screen.values()

    TabRow(
        modifier= modifier,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor =MaterialTheme.colors.primary,
        contentColor = CardCoverPink,
        divider = {
            TabRowDefaults.Divider(
                color = Color.White
            )
        },
        tabs = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab.title,
                            color = if (pagerState.currentPage == index) Color.White  else CardCoverPink,
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
