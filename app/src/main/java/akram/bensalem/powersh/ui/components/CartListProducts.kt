package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHRed
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.utils.PARTICLE_LENGTH
import akram.bensalem.powersh.utils.Particle
import akram.bensalem.powersh.utils.generateParticle
import akram.bensalem.powersh.utils.localization.Locales
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


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

    val isRemove = remember {
        mutableStateOf(false)
    }


    var isComputed = remember { mutableStateOf(false) }
    var height = remember { mutableStateOf(0) }
    var width = remember { mutableStateOf(0) }

    val animationScope = rememberCoroutineScope()

    val alpha = remember { Animatable(initialValue = 0f) }

    val particles = remember { arrayOfNulls<Particle>(PARTICLE_LENGTH * PARTICLE_LENGTH) }

    val factor = remember { Animatable(initialValue = 0f) }


    val animatedProgress = remember {
        Animatable(initialValue = 0.8f)
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
        modifier = animatedModifier
            .fillMaxWidth()
            .onGloballyPositioned {

                if (!isComputed.value) {
                    height.value = it.size.height
                    width.value = it.size.width

                    val random = Random(System.currentTimeMillis())
                    for (i in 0 until PARTICLE_LENGTH) {
                        for (j in 0 until PARTICLE_LENGTH) {
                            particles[(i * PARTICLE_LENGTH) + j] = generateParticle(
                                random,
                                height.value,
                                width.value / 2,
                                height.value / 2
                            )
                        }
                    }
                }


            }
            .drawWithContent {
                this.drawContent()
                for (particle in particles) {
                    particle?.let {
                        it.update(factor.value)
                        if (it.alpha > 0f) {
                            this.drawCircle(
                                PowerSHRed,
                                it.radius,
                                Offset(it.cx, it.cy)
                            )
                        }
                    }
                }
            }
            .alpha(1f - alpha.value)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
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
                            .size(130.dp)
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
                    .weight(1f)
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
                    subtitleData = LocalStrings.current.totalPriceValue(product.price)
                )

            }

            IconButton(
                onClick = {
                    //   onRemove()
                    animationScope.launch {
                        if (!isComputed.value) {

                            val random = Random(System.currentTimeMillis())
                            for (i in 0 until PARTICLE_LENGTH) {
                                for (j in 0 until PARTICLE_LENGTH) {
                                    particles[(i * PARTICLE_LENGTH) + j] = generateParticle(
                                        random,
                                        height.value,
                                        width.value / 2,
                                        height.value / 2
                                    )
                                }
                            }
                            isComputed.value = true
                        }

                        val result = async {
                            // Run the alpha animation
                            alpha.animateTo(
                                targetValue = 1f,
                                animationSpec = keyframes {
                                    this.durationMillis = durationMillis
                                    0.9f at durationMillis / 2
                                }
                            )
                        }



                        // Run the particle animation
                        async {
                            factor.animateTo(
                                targetValue = 1.5f,
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = LinearEasing
                                )
                            )

                        }


                        result.invokeOnCompletion {
                            this.launch {
                                delay(300)
                                alpha.snapTo(0f)
                                factor.snapTo(0f)
                                onRemove()
                            }

                        }


                    }
                },

                modifier = Modifier
                    .background(shape = CircleShape, color = Color.Transparent)
                    .align(Alignment.CenterVertically)
                    .padding(4.dp, 0.dp)

            ) {
                Icon(
                    imageVector = Icons.Outlined.DeleteForever,
                    contentDescription = LocalStrings.current.removeAll,
                    tint = Color.LightGray,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically)
                        .graphicsLayer {
                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                        },
                )
            }
        }
    }

}


