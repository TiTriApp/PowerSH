package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.Shapes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteForever
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun cartListProducts(
    cartProduct: MutableList<CardItem>,
    onRemoveItem: (Int) -> Unit

) {
    LazyColumn(
        modifier = Modifier.padding(start = 0.dp, end = 0.dp),
        contentPadding = PaddingValues(
            top = 2.dp,
            bottom = 2.dp
        )
    ) {

        itemsIndexed(cartProduct) { index, row ->
            cardItemEntry(
                product = row,
                onRemove = { onRemoveItem(index) },
                modifier = Modifier
                    .padding(
                        end = 0.dp,
                    )
            )

            Spacer(modifier = Modifier.padding(4.dp))

        }
    }


}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun cardItemEntry(
    modifier: Modifier = Modifier,
    onRemove: () -> Unit = {},
    product: CardItem
) {
    //Scale animation
    val animatedProgress = remember {
        androidx.compose.animation.core.Animatable(initialValue = 1.15f)
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
        data = product.ImageId,
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
        modifier = animatedModifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    Modifier
                        .size(130.dp)
                        .align(Alignment.Center)
                        .background(
                            color = CardCoverPink.copy(alpha = 0.2f),
                            shape = Shapes.large,
                        )
                ) {}

                if (imageLoading) {
                    LoadingImage(
                        modifier = Modifier
                            .size(162.dp)
                            .padding(Dimens.MediumPadding.size)
                    )
                }
                Image(
                    painter = coilPainter,
                    contentDescription = "${product.title} Image",
                    modifier = Modifier
                        .size(130.dp)
                        .padding(Dimens.MediumPadding.size)
                )
            }
            Text(
                text = "x${product.quantity}",
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(start = 24.dp, end = 24.dp)
                    .align(Alignment.CenterVertically)
            )
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
                    text = product.title,
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                SubtitleText(
                    subtitleName = LocalStrings.current.color,
                    subtitleData = product.color
                )
                SubtitleText(
                    subtitleName = LocalStrings.current.size,
                    subtitleData = product.size.toString()
                )
                SubtitleText(
                    subtitleName = LocalStrings.current.marketValue,
                    subtitleData = product.price.toString()
                )

            }

            IconButton(
                onClick = {
                    onRemove()
                },

                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Transparent)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteForever,
                    contentDescription = LocalStrings.current.removeAll,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }

}


