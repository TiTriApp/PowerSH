package akram.bensalem.powersh.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.theme.BlueLinkColor
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants

@Composable
fun ThinkpadDetails(
    modifier: Modifier = Modifier,
    shoeProduct: ShoeProduct,
    onExternalLinkClick: () -> Unit = { }
) {
    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(500, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )

    val divider = " - "
    val marketPrice by remember(shoeProduct) {
        derivedStateOf {
            buildAnnotatedString {
                append("$${shoeProduct.marketPriceStart}")
                append(divider)
                append("$${shoeProduct.marketPriceEnd}")
            }.text
        }
    }

    var maxLines by remember {
        mutableStateOf(1)
    }

    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180F else 0F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = animatedModifier
            .fillMaxWidth()
            .padding(Dimens.MediumPadding.size)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = shoeProduct.title,
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(horizontal = Dimens.MediumPadding.size)
            )
            IconButton(
                onClick = onExternalLinkClick,
                modifier = Modifier
                    .padding(horizontal = Dimens.SmallPadding.size)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Launch,
                    contentDescription = stringResource(id = R.string.external_link),
                    tint = BlueLinkColor
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = Shapes.large
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ),
            contentAlignment = Alignment.CenterStart
        ) {

            Column(
                modifier = Modifier
                    .padding(Dimens.MediumPadding.size)
            ) {
                SubtitleTextWithIcon(
                    subtitleName = "Release Date",
                    subtitleData = shoeProduct.releaseDate,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Outlined.Today,
                    maxLines = maxLines
                )
                SubtitleTextWithIcon(
                    subtitleName = "Market Value",
                    subtitleData = marketPrice,
                    style = MaterialTheme.typography.body1,
                    icon = Icons.Outlined.Loyalty,
                    maxLines = maxLines
                )

            }

            IconButton(
                onClick = {
                    maxLines = if (maxLines == 1) {
                        expanded = true
                        Int.MAX_VALUE
                    } else {
                        expanded = false
                        1
                    }
                },
                modifier = Modifier
                    .padding(
                        top = Dimens.SmallPadding.size,
                        end = Dimens.SmallPadding.size
                    )
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ExpandMore,
                    contentDescription = stringResource(id = R.string.expand_icon),
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier.rotate(angle)
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun ThinkpadFeaturesPreview() {
    PowerSHTheme {
        ThinkpadDetails(shoeProduct = Constants.ShoesListPreview[0])
    }
}

@Composable
fun SubtitleTextWithIcon(
    subtitleName: String,
    subtitleData: String,
    style: TextStyle = MaterialTheme.typography.subtitle1,
    maxLines: Int = 1,
    iconDescription: String? = null,
    icon: ImageVector
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(vertical = Dimens.SmallPadding.size)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = MaterialTheme.colors.onSurface,
        )
        Spacer(modifier = Modifier.width(Dimens.MediumPadding.size))
        SubtitleText(
            verticalPadding = 0.dp,
            subtitleName = subtitleName,
            subtitleData = subtitleData,
            style = style,
            maxLines = maxLines,
            verticalAlignment = Alignment.Top
        )
    }
}