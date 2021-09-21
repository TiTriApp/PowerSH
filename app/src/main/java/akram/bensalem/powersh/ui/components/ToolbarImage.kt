package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.details.HorizontalPagerWithOffsetTransition
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter

@ExperimentalAnimationApi
@Composable
fun ToolbarImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {

    var imageLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            listener(
                onStart = {
                    imageLoading = true
                },
                onSuccess = { _, _ ->
                    imageLoading = false
                }
            )
        }
    )

 /*   Image(
        painter = coilPainter,
        contentDescription = LocalStrings.current.imageOfPowerSHShoes,
        modifier = modifier
            .padding(Dimens.MediumPadding.size)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
            .graphicsLayer {
                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
            }
    )
*/

    HorizontalPagerWithOffsetTransition(
        painter = coilPainter,
        modifier =   modifier
            .padding(Dimens.MediumPadding.size)
            .animateContentSize(
                animationSpec = tween(500, easing = LinearOutSlowInEasing)
            )
            .graphicsLayer {
                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
            }
    )



    AnimatedVisibility(
        visible = imageLoading,
        enter = scaleIn(),
        exit = scaleOut()
    ) {
        CircularProgressIndicator()
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun TopCardWithImagePreview() {
    PowerSHTheme {
        ToolbarImage(
            imageUrl = "https://raw.githubusercontent.com/akram/main/shoe.png",
        )
    }
}
