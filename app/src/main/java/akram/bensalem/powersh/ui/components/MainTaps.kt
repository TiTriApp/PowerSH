package akram.bensalem.powersh.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.theme.PowerSHRed
import akram.bensalem.powersh.ui.theme.PowerSHTheme

data class ScreenState(var state: Screen = Screen.TOUS) {

    enum class Screen(
        val title: String = "Tab",
        val icon: Int = R.drawable.men,
    ) {
        TOUS(title = "All", icon = R.drawable.men),
        HOMME(title = "Men", icon = R.drawable.man),
        FEMME(title = "Women", icon = R.drawable.woman2),
        ENFANT(title = "Baby", icon = R.drawable.baby)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsPanel(
    pagerState: PagerState,
    onTabSelected : (Int) -> Unit = {},
) {


    val tabs = ScreenState.Screen.values()

    ScrollableTabRow(

        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.background,
        edgePadding = 16.dp,
        contentColor = PowerSHRed,
        indicator = {},
        divider = {},
        tabs = {
            tabs.forEachIndexed { index, tab ->
                    CategoryTab(
                        category = tab.title,
                        icon = tab.icon,
                        selected = index == pagerState.currentPage,
                        onClick = {   onTabSelected(index) },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
                    )

            }
        }
    )
}











private enum class CategoryTabState { Selected, NotSelected }

@Composable
private fun CategoryTab(
    category: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Int
) {

    val transition = updateTransition(if (selected) CategoryTabState.Selected else CategoryTabState.NotSelected)

    val backgroundColor by transition.animateColor { state ->
        when (state) {
            CategoryTabState.Selected -> MaterialTheme.colors.primary
            CategoryTabState.NotSelected -> MaterialTheme.colors.surface
        }
    }
    val contentColor by transition.animateColor { state ->
        when (state) {
            CategoryTabState.Selected -> LocalContentColor.current
            CategoryTabState.NotSelected -> MaterialTheme.colors.primary
        }
    }

    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = backgroundColor,
        contentColor = contentColor,
        border = BorderStroke(2.dp, MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = "categories",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(32.dp),
            )
            /*NetworkImage(
                imageUrl = "https://raw.githubusercontent.com/hitanshu-dhawan/McCompose/main/app/src/main/res/drawable-nodpi/category_burgers.png",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(32.dp),
                previewPlaceholder = com.akram.bensalem.powersh.R.drawable.dish_2
            )*/
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category,
                color = if (selected) Color.White else MaterialTheme.colors.primary,
                style = MaterialTheme.typography.button
            )
        }
    }
}











@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun splashPreview() {

    val pagerState = rememberPagerState(pageCount = 4)

    PowerSHTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {


            TabsPanel(
                pagerState = pagerState,
            )
        }
    }
}