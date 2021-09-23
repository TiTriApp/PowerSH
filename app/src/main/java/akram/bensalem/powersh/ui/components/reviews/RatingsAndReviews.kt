package akram.bensalem.powersh.ui.components.reviews


import akram.bensalem.powersh.data.model.reviews
import akram.bensalem.powersh.ui.components.surfaces.PowerSHSurface
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RatingsAndReviews() {
    Column(
        modifier = Modifier.padding(top = 4.dp, start = 24.dp, end = 16.dp, bottom = 8.dp)
    ) {
        RatingsAndReviewsHeader()
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = "4.7",
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 45.sp,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.align(Alignment.Start)
                )
                StarRatings()
                Text(
                    text = "2,907,517",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        letterSpacing = 0.70.sp
                    ),
                    color = MaterialTheme.colors.onSurface
                )
            }
            AppRatingBars(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        val reviews = reviews
        Column {
            reviews.forEach {
            Spacer(modifier = Modifier.height(16.dp))
            ReviewItem(review = it)
        }
        }
    }
}

@Composable
private fun RatingsAndReviewsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Ratings and reviews",
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp
            ),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.Top)
        ) {
            Icon(
                imageVector = Outlined.ArrowForward,
                tint = MaterialTheme.colors.onSurface,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun AppRatingBars(modifier: Modifier) {
    val showProgress = remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                text = "5",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedProgressIndicator(
                progress = 0.8f,
                durationMillis = 4000,
                color = MaterialTheme.colors.primary,
                showProgress = showProgress
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row {
            Text(
                text = "4",
                style = MaterialTheme.typography.caption,
                color =MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedProgressIndicator(
                progress = 0.5f,
                color =  Color.White,
                showProgress = showProgress
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row {
            Text(
                text = "3",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedProgressIndicator(
                progress = 0.3f,
                durationMillis = 4000,
                color = MaterialTheme.colors.primary,
                showProgress = showProgress
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row {
            Text(
                text = "2",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedProgressIndicator(
                progress = 0.1f,
                color =MaterialTheme.colors.primary,
                showProgress = showProgress
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row {
            Text(
                text = "1",
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            AnimatedProgressIndicator(
                progress = 0.2f,
                durationMillis = 4000,
                color = MaterialTheme.colors.primary,
                showProgress = showProgress
            )
        }
    }
    showProgress.value = true
}

@Preview("Ratings and Reviews")
@Composable
private fun ReviewsPreview() {
    PowerSHTheme() {
        PowerSHSurface() {
            RatingsAndReviews()
        }
    }
}

@Preview("Ratings and Reviews Dark")
@Composable
private fun ReviewsDarkPreview() {
    PowerSHTheme(theme = 2) {
        PowerSHSurface {
            RatingsAndReviews()
        }
    }
}