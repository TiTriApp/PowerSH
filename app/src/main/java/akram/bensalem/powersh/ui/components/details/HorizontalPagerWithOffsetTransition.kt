package akram.bensalem.powersh.ui.components.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import coil.compose.ImagePainter

import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HorizontalPagerWithOffsetTransition(
    modifier: Modifier = Modifier,
    painter: ImagePainter
) {
    val state = PagerState(10)

    HorizontalPager(
        state = state,
        modifier = Modifier.fillMaxWidth(),

    ) { page ->
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                lerp(
                    start = 0.7.dp,
                    stop = 1.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).also { scale ->
                    scaleX = scale.value
                    scaleY = scale.value
                }

                alpha = lerp(
                    start = 0.1.dp,
                    stop = 1.dp,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).value
            },
        )


    }
}
