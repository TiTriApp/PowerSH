package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.components.CollapsingToolbarBase
import akram.bensalem.powersh.ui.components.DetailsCards
import akram.bensalem.powersh.ui.components.ToolbarImage
import akram.bensalem.powersh.ui.components.details.HorizontalPagerWithOffsetTransition
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding

@ExperimentalAnimationApi
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    shoeProduct: ShoeProduct,
    cartProduct: MutableList<CardItem>,
    listState: LazyListState = rememberLazyListState(),
    favourite: MutableState<Boolean>,
    onNavigateToCartScreen: () -> Unit,
    onBackButtonPressed: () -> Unit = { },
    onFavouriteClick: () -> Unit = { },
) {
    val toolbarHeight = 250.dp
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

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = onBackButtonPressed
            ) {
                ToolbarImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    imageUrl = shoeProduct.imageUrl
                )

            }
        }
    ) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                DetailsCards(
                    shoeProduct = shoeProduct,
                    cartProduct = cartProduct,
                    onFavouriteClick = onFavouriteClick,
                    favourite = favourite,
                    onNavigateToCartScreen = onNavigateToCartScreen
                )
            }

            // Always at the bottom
            item { Spacer(modifier = Modifier.navigationBarsPadding()) }
        }
    }


}


@ExperimentalAnimationApi
@Preview(
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    device = Devices.PIXEL_4
)
@Composable
private fun DetailsScreenPreview() {

    val favourite = remember {
        mutableStateOf(false)
    }

    val cartProduct = remember { Constants.cartList }

    PowerSHTheme {
        DetailsScreen(shoeProduct = Constants.ShoesListPreview[0],
            cartProduct = cartProduct,
            favourite = favourite, onNavigateToCartScreen = {}
        )
    }
}
