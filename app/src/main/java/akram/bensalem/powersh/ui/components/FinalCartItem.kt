package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.utils.BalloonUtils
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.skydoves.orchestra.tooltips.BalloonAnchor


@OptIn(ExperimentalCoilApi::class)
@Composable
fun finalCartItem(
    modifier: Modifier = Modifier,
    product: CardItem,
    onInfo: () -> Unit = {},
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //Scale animation
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
                    subtitleName = "Color",
                    subtitleData = product.color
                )
                SubtitleText(
                    subtitleName = "Size",
                    subtitleData = product.size.toString()
                )
                SubtitleText(
                    subtitleName = "Market Value",
                    subtitleData = product.price.toString()
                )

            }

            ConstraintLayout(
                Modifier
                    .align(Alignment.CenterVertically)
                    .height(40.dp)
            ) {
                val (image) = createRefs()
                IconButton(
                    onClick = {
                        onInfo()
                    },
                    modifier = Modifier
                        .constrainAs(image) {
                            end.linkTo(parent.end)
                        }
                        .background(shape = CircleShape, color = Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Detail item",
                        tint = Color.LightGray,
                        modifier = Modifier
                            .size(24.dp)
                    )

                }
                BalloonAnchor(
                    reference = image,
                    modifier = Modifier.fillMaxWidth(),
                    balloon = BalloonUtils.getEditBalloon(
                        context = context,
                        lifecycleOwner = lifecycleOwner,
                        description = product.description
                    ),
                    onAnchorClick = { balloon, anchor -> balloon.show(anchor) }
                )


            }


        }
    }

}



