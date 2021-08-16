package work.racka.thinkrchive.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme
import work.racka.thinkrchive.utils.Constants

@Composable
fun ThinkpadEntry(
    onEntryClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    thinkpad: Thinkpad
) {
    val divider = " - "
    val marketPrice by remember(thinkpad) {
        derivedStateOf {
            buildAnnotatedString {
                append("$${thinkpad.marketPriceStart}")
                append(divider)
                append("$${thinkpad.marketPriceEnd}")
            }.text
        }
    }

    var imageLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = thinkpad.imageUrl,
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
    
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .clip(Shapes.large)
            .background(color = MaterialTheme.colors.surface,)
            .clickable { onEntryClick() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (imageLoading) {
                    LoadingImage(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                }
                Image(
                    painter = coilPainter,
                    contentDescription = "${thinkpad.model} Image",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(Dimens.MediumPadding.size)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(
                        top = Dimens.MediumPadding.size,
                        bottom = Dimens.MediumPadding.size,
                        end = Dimens.MediumPadding.size
                    )
            ) {
                Text(
                    text = thinkpad.model,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SubtitleText(
                    subtitleName = "Platform",
                    subtitleData = thinkpad.processorPlatforms
                )
                SubtitleText(
                    subtitleName = "Market Value",
                    subtitleData = marketPrice
                )
                SubtitleText(
                    subtitleName = "Release",
                    subtitleData = thinkpad.releaseDate
                )
            }
        }
    }
}

@Composable
private fun SubtitleText(
    subtitleName: String,
    subtitleData: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$subtitleName:",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = subtitleData,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun LoadingImage(
    modifier: Modifier = Modifier
        .size(120.dp)
) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.15f at 500
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.onSurface
                    .copy(alpha = alpha),
                shape = Shapes.large
            )
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SubtitleTextPreview() {
    ThinkRchiveTheme {
        SubtitleText(
            subtitleName = "Market Price",
            subtitleData = "$250 - $2050"
        )
    }
}

@Preview
@Composable
private fun ThinkpadEntryPreview() {
    ProvideWindowInsets {
        ThinkRchiveTheme {
            val thinkpad = (Constants.ThinkpadsListPreview[0]).apply {
                ThinkpadEntry(
                    thinkpad = this,
                    modifier = Modifier
                        .padding(Dimens.MediumPadding.size)
                        .statusBarsPadding()
                )
            }
        }
    }
}
