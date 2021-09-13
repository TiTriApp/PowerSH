package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun ProductShoesEntry(
    modifier: Modifier = Modifier,
    onEntryClick: () -> Unit = {},
    shoeProduct: ShoeProduct
) {
    val animatedProgress = remember {
        Animatable(initialValue = 1.15f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )

    var imageLoading by remember {
        mutableStateOf(true)
    }
    val coilPainter = rememberImagePainter(
        data = shoeProduct.imageUrl,
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

    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = Dimens.ElevationPadding.size,
        modifier = animatedModifier
            .clickable { onEntryClick() }

    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
            ) {



            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                        .background(
                            color = CardCoverPink.copy(alpha = 0.2f)
                        )
                ) {}

                if (imageLoading) {
                    LoadingImage(
                        modifier = Modifier
                            .size(130.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                }
                Image(
                    painter = coilPainter,
                    contentDescription = "${shoeProduct.title} Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(vertical = Dimens.SmallPadding.size, horizontal = Dimens.SmallPadding.size)
                )



            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = Dimens.MediumPadding.size)
                ) {
                    Text(
                        text = shoeProduct.title,
                        style = MaterialTheme.typography.body1,
                        maxLines=1,
                        color = MaterialTheme.colors.onBackground,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier.padding(
                            top = Dimens.MiniSmallPadding.size,
                            bottom = Dimens.SmallPadding.size
                        )
                    ) {
                        if (shoeProduct.marketPriceStart != shoeProduct.marketPriceEnd) {
                            Text(
                                text = "${shoeProduct.marketPriceEnd} DA",
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.subtitle2,
                                textDecoration = TextDecoration.LineThrough,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(end = Dimens.MiniSmallPadding.size)
                            )
                        }
                        Text(
                            text = "${shoeProduct.marketPriceStart} DA",
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.subtitle2,
                            fontWeight = FontWeight.Medium,
                            maxLines=1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                        )
                    }

                }

                IconButton(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    onClick = {  }) {
                    Icon(
                        imageVector = Icons.Outlined.Info ,
                        contentDescription = "More",
                        tint = Color.LightGray
                    )
                }
            }

        }

    }



}


@Composable
fun SubtitleText(
    verticalPadding: Dp = 4.dp,
    subtitleName: String,
    subtitleData: String,
    style: TextStyle = MaterialTheme.typography.body2,
    maxLines: Int = 1,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically
) {
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(vertical = verticalPadding)
    ) {
        Text(
            text = "$subtitleName:",
            style = style,
            color = MaterialTheme.colors.onSurface,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = subtitleData,
            style = style,
            color = MaterialTheme.colors.onSurface,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LoadingImage(
    modifier: Modifier = Modifier
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

@Preview
@Composable
private fun ProductShoesEntryPreview() {
    ProvideWindowInsets {
        PowerSHTheme {
            Constants.ShoesListPreview[0].apply {
                ProductShoesEntry(
                    shoeProduct = this,
                    modifier = Modifier
                        .padding(Dimens.MediumPadding.size)
                        .statusBarsPadding()
                )
            }
        }
    }
}
