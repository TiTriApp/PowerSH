package akram.bensalem.powersh.ui.components.authentication

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.components.loginTabs
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState


@ExperimentalPagerApi
@Composable
fun MainCard(
    modifier: Modifier = Modifier,
    pagerState: PagerState = PagerState(2),
    onBackButtonPressed: () -> Unit = { },
    shape: Shape = RoundedCornerShape(
        bottomEnd = 10.dp,
        bottomStart = 10.dp
    ),
    backgroundColor: Color = MaterialTheme.colors.primary,
    toolbarHeight: Dp,
    minShrinkHeight: Dp = 70.dp,
    toolbarOffset: Float,
    onPageSelected: (Int) -> Unit = {},
) {
    val animatedProgress = remember {
        Animatable(initialValue = 0.9f)
    }

    val scrollDp = toolbarHeight + toolbarOffset.dp
    val animatedCardSize by animateDpAsState(
        targetValue = if (scrollDp <= minShrinkHeight) 100.dp else scrollDp,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )
    val animatedElevation by animateDpAsState(
        targetValue = if (scrollDp < minShrinkHeight + 20.dp) 10.dp else 0.dp,
        animationSpec = tween(500, easing = LinearOutSlowInEasing)
    )

    LaunchedEffect(key1 = animatedProgress) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value
        )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(32.dp).copy(
                    topStart = ZeroCornerSize,
                    topEnd = ZeroCornerSize
                )
            )
            .shadow(
                elevation = animatedElevation,
                shape = shape
            )
            .background(
                color = backgroundColor,
            )
    ) {
        Box(
            modifier = animatedModifier
                .fillMaxWidth()
                .height(animatedCardSize)
        ) {

            IconButton(
                onClick = onBackButtonPressed,
                modifier = Modifier
                    .padding(Dimens.SmallPadding.size)
                    .align(Alignment.TopStart)
                    .clickable(
                        onClick = { onBackButtonPressed() },
                        indication = rememberRipple(color = Color.White),
                        interactionSource = remember { MutableInteractionSource() },
                    ),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = LocalStrings.current.goBack,
                    tint = Color.White,
                )
            }


            Image(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .size(80.dp)
                    .align(Alignment.Center)
                    .animateContentSize(
                        animationSpec = tween(500, easing = LinearOutSlowInEasing)
                    ),
                painter = painterResource(id = R.drawable.powersh_without_background),
                contentDescription = LocalStrings.current.appLogo,
                contentScale = ContentScale.Crop,
            )

        }

        loginTabs(
            modifier = animatedModifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = shape
                ),
            pagerState = pagerState
        ) {
            onPageSelected(it)
        }

    }


}


@ExperimentalPagerApi
@Preview
@Composable
fun MainCardPrev() {
    PowerSHTheme {
        MainCard(
            toolbarOffset = 0f,
            toolbarHeight = 200.dp,
        )
    }
}