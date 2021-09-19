package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp



private enum class NextModeState { FILL, EMPTY }


@ExperimentalAnimationApi
@Composable
fun BottomBarCheckoutScreen(
    step: MutableState<Int> = remember {
        mutableStateOf(0)
    },
    enabled: Boolean,
    onBackPressed: () -> Unit = {},
    onNextPressed: () -> Unit = {},

){


    val transition = updateTransition(
        if (enabled) NextModeState.FILL else NextModeState.EMPTY,
        label = ""
    )

    val backgroundColor by transition.animateColor { state ->
        when (state) {
            NextModeState.FILL -> MaterialTheme.colors.primary
            NextModeState.EMPTY -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            NextModeState.FILL -> MaterialTheme.colors.primary
            NextModeState.EMPTY -> LocalContentColor.current
        }
    }




    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 16.dp)
    ) {

        AnimatedVisibility(
            visible = step.value > 0,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally(
                targetOffsetX = { -it },
                spring()
            ),
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    onBackPressed()
                }) {
                Text(
                    text = LocalStrings.current.back,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    )
                )
            }


        }

        Spacer(
            modifier =
            Modifier
                .weight(1f)
        )



        Button(
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                disabledBackgroundColor = backgroundColor,
                disabledContentColor = contentColor
            ),

            modifier = Modifier
                .align(Alignment.CenterVertically),
            enabled= enabled,
            onClick = {
               onNextPressed()
            }) {
            Text(
                text = if (step.value < 2) LocalStrings.current.next else LocalStrings.current.confirm,
                color = if (enabled) Color.White else MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 2.dp,
                    bottom = 2.dp
                )
            )
        }
    }
}