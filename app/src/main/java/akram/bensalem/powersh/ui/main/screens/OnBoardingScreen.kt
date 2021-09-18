package com.akram.bensalem.powersh.ui.screens.onboarding

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.OnBoardingItem
import akram.bensalem.powersh.ui.main.viewModel.OnBoardingViewModel
import akram.bensalem.powersh.ui.theme.Dimens
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.esi.sba.powersh.components.indicator.PageIndicator
import com.esi.sba.powersh.components.indicator.Pager
import com.esi.sba.powersh.components.indicator.PagerState
import com.google.accompanist.insets.navigationBarsPadding

const val currentPageAnimation = "currentPageAnimation"
const val rotateAnimation = "rotateAnimation"

@ExperimentalAnimationApi
@Composable
fun OnBoardingContent(
    onActionClicked: () -> Unit = { },
) {

    val viewModel: OnBoardingViewModel = hiltViewModel()
    val onBoardingItemsList = viewModel.getOnBoardingItemsList()
    val launchDestination: Boolean by viewModel.viewState.collectAsState()

    if (launchDestination) {
        onActionClicked()
    }

    val pagerState = remember { PagerState() }
    pagerState.maxPage = (onBoardingItemsList.size - 1).coerceAtLeast(0)

    val transition =
        updateTransition(targetState = pagerState.currentPage, label = currentPageAnimation)
    val rotation by transition.animateFloat(
        {
            tween(durationMillis = 1000)
        },
        label = rotateAnimation
    ) {
        if (it == 0) 0f else it * 360f
    }

    val getStartedVisible = remember { mutableStateOf(false) }

    getStartedVisible.value = pagerState.currentPage == pagerState.maxPage

    val color = onBoardingItemsList[pagerState.currentPage].color
    val backgroundColor by animateColorAsState(
        color,
        spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
    )



    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (options, wave, waveBody) = createRefs()
        val guidelineWave = createGuidelineFromTop(0.5f)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .constrainAs(waveBody) {
                    top.linkTo(parent.top)
                    bottom.linkTo(guidelineWave)
                    height = Dimension.fillToConstraints
                }
        )
        Image(
            painter = painterResource(R.drawable.ic_wave),
            contentDescription = "image",
            modifier = Modifier
                .aspectRatio(4.5f)
                .constrainAs(wave) {
                    top.linkTo(guidelineWave)
                },
            colorFilter = ColorFilter.tint(backgroundColor)
        )
        Pager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) {
            OnboardingPageItem(
                onBoardingItemsList[page], rotation = rotation
            )
        }
        OnboardingPageOptions(
            getStartedVisible = getStartedVisible,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Dimens.MediumPadding.size,
                    start = Dimens.MediumPadding.size,
                    end = Dimens.MediumPadding.size
                )
                .navigationBarsPadding(bottom = true)
                .constrainAs(options) {
                    bottom.linkTo(parent.bottom)
                },
            skip = {
                viewModel.getStartedClick()
            },
        ) {
            if (pagerState.currentPage != onBoardingItemsList.size - 1) {
                pagerState.currentPage = pagerState.currentPage + 1
            }
            if (pagerState.currentPage != onBoardingItemsList.size - 2) {
                getStartedVisible.value = true
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingPageOptions(
    getStartedVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    currentPage: Int,
    skip: () -> Unit,
    next: () -> Unit,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = Color.White
    ) {
        AnimatedVisibility(
            visible = getStartedVisible.value,
            enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(
                expandFrom = Alignment.Top
            ) + fadeIn(initialAlpha = 0.3f)
        ) {
            Button(
                onClick = {
                    skip()
                },
                modifier = Modifier
                    .padding(vertical = 32.dp, horizontal = 120.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = com.akram.bensalem.powersh.ui.R.string.onBoarding_start,
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = !getStartedVisible.value,
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {
                        skip()
                    }
                ) {
                    Text(
                        text = "Skip",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.body2
                    )
                }


                PageIndicator(
                    pagesCount = 3,
                    currentPageIndex = currentPage,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                )

                TextButton(
                    onClick = {
                        next()
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = "Next",
                        color = MaterialTheme.colors.primary,
                        style = MaterialTheme.typography.button,
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowRight,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
fun OnboardingPageItem(item: OnBoardingItem, rotation: Float) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                item.contentImageId
            )
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
        )



        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .requiredSize(250.dp, 250.dp)
                .rotate(rotation),
        )

        Text(
            text = item.titleId,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(
                start = 30.dp, top = 16.dp, end = 30.dp
            )
        )
        Text(
            text = item.DescriptionId,
            color = Color.Gray,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 30.dp, top = 16.dp, end = 30.dp),
        )
    }
}

