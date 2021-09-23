package akram.bensalem.powersh.ui.components.reviews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StarRatings(
    modifier: Modifier = Modifier,
    ratings: Double = 3.5,
    sizeInDp: Dp = 15.dp
) {
    Box(modifier = modifier) {
        Row {
            for (i in 1..5) {
                Star(sizeInDp)
            }
        }
        Row {
            for (i in 1..ratings.toInt()) {
                StarFilled(sizeInDp)
            }
        }
    }
}

@Composable
fun Star(sizeInDp: Dp) {
    Icon(
        imageVector = Icons.Filled.Star, tint = Color.White,
        modifier = Modifier
            .height(sizeInDp)
            .width(sizeInDp),
        contentDescription = null
    )
}

@Composable
fun StarFilled(sizeInDp: Dp) {
    Icon(
        imageVector = Icons.Filled.Star, tint = MaterialTheme.colors.primary,
        modifier = Modifier
            .height(sizeInDp)
            .width(sizeInDp),
        contentDescription = null
    )
}