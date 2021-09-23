package akram.bensalem.powersh.ui.components.reviews

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp





import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.State

enum class AppRatingBarState {
    START,
    END
}

@Composable
fun getAppRatingBarState(
    progress: Float,
    durationMillis: Int = 3000,
    showProgress: Boolean
): State<Float> {
    val currentState = if (showProgress) AppRatingBarState.END else AppRatingBarState.START
    val transition = updateTransition(targetState = currentState, label = "appRatingBarState")
    return transition.animateFloat(
        transitionSpec = {
            when {
                AppRatingBarState.START isTransitioningTo AppRatingBarState.END ->
                    tween(easing = FastOutSlowInEasing, durationMillis = durationMillis)
                AppRatingBarState.END isTransitioningTo AppRatingBarState.START -> {
                    tween(easing = FastOutSlowInEasing, durationMillis = durationMillis)
                }
                else -> snap()
            }
        }, label = "appRatingBarState"
    ) { appRatingBarState ->
        when (appRatingBarState) {
            AppRatingBarState.START -> 0f
            AppRatingBarState.END -> progress
        }
    }
}



@Composable
fun AnimatedProgressIndicator(
    modifier: Modifier = Modifier.padding(start = 8.dp, end = 8.dp),
    progress: Float,
    durationMillis: Int = 3000,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = 9.dp,
    backgroundColor: Color =  Color.White,
    showProgress: MutableState<Boolean>
) {
    val state = getAppRatingBarState(
        progress = progress, durationMillis = durationMillis, showProgress = showProgress.value
    )

    Canvas(
        modifier
            .progressSemantics(state.value)
            .size(280.dp, strokeWidth)
    ) {
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(15f, 15f),
            size = Size(size.width, size.height)
        )
        drawRoundRect(
            color = color, cornerRadius = CornerRadius(15f, 15f),
            size = Size(state.value * size.width, size.height)
        )
    }
}