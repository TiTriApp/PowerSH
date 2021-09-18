package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.data.model.Step
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun StepItem(
    s: Step,
    index: Int,
    lastIndex: Int,
    selected: Int,
    modifier: Modifier
) {

    val color = if (index <= selected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface


    val backgroundColor by animateColorAsState(
        color,
        spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
    )


    ConstraintLayout(
        modifier = modifier.padding(top = 8.dp, end = 8.dp)
    ) {
        val (circle, text, leftLine, rightLine) = createRefs()
        Spacer(
            modifier =
            Modifier
                .height(1.dp)
                .fillMaxWidth(0.5f)
                .constrainAs(leftLine) {
                    start.linkTo(parent.start)
                    end.linkTo(circle.start)
                    top.linkTo(circle.top)
                    bottom.linkTo(circle.bottom)
                }
                .background(if (index == 0) Color.Transparent else backgroundColor)
        )

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.5f)
                .constrainAs(rightLine) {
                    start.linkTo(circle.end)
                    top.linkTo(circle.top)
                    bottom.linkTo(circle.bottom)
                }
                .background(
                    if (index == lastIndex) Color.Transparent else {
                        if (selected > index) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    }
                )
        )


        Box(
            Modifier
                .padding(0.dp)
                .background(color = MaterialTheme.colors.surface)
                .border(1.dp, backgroundColor, CircleShape)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }

        )
        {
            Text(
                text = "",
                modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .background(color = backgroundColor, shape = CircleShape)
            )
        }


        Text(
            text = s.title,
            color = backgroundColor,
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    top.linkTo(circle.bottom)
                }

        )


    }


}
