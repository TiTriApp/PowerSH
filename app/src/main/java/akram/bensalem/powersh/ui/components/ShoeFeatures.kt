package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.details.HorizontalPagerWithOffsetTransition
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.localization.Locales
import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShoeFeatures(
    modifier: Modifier = Modifier,
    shoeProduct: ShoeProduct,
    cartProduct: MutableList<CardItem>,
    quantity: MutableState<Int>,
    isColorDialogVisible: MutableState<Boolean>,
    colorSelected: MutableState<String>,
    isSizeDialogVisible: MutableState<Boolean>,
    sizeSelected: MutableState<Int>,
    isAddedToCart: MutableState<Boolean>,
    onNavigateToCartScreen: () -> Unit,
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


    Column(
        modifier = animatedModifier
            .fillMaxWidth()
            .padding(Dimens.MediumPadding.size)
    ) {
        Text(
            text = LocalStrings.current.paymentDetail,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .padding(
                    bottom = Dimens.MediumPadding.size,
                    start = Dimens.MediumPadding.size
                )
                .align(Alignment.Start)
        )

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
                    .padding(top = Dimens.MiniSmallPadding.size)
            ) {

                features(
                    quantity = quantity,
                    isColorDialogVisible = isColorDialogVisible,
                    colorSelected = colorSelected,
                    isSizeDialogVisible = isSizeDialogVisible,
                    sizeSelected = sizeSelected,
                    isAddedToCart = isAddedToCart
                )

                AnimatedVisibility(
                    visible = isAddedToCart.value,
                    enter = fadeIn(
                        initialAlpha = 0f
                    ),
                    exit = fadeOut(
                        targetAlpha = 0f
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    CartButton(
                        quantity = quantity.value,
                        price = (quantity.value * shoeProduct.marketPriceStart).toDouble(),
                        onClick = {
                            onNavigateToCartScreen()

                        }
                    )
                }

                payment(
                    price = shoeProduct.marketPriceStart,
                    quantity = quantity,
                    isAddedToCart = isAddedToCart,
                    onIncrementQuantity = {
                        cartProduct.add(
                            CardItem(
                                id = 1,
                                title = shoeProduct.title,
                                price = shoeProduct.marketPriceEnd,
                                quantity = quantity.value,
                                ImageId = shoeProduct.imageUrl,
                                color = colorSelected.value,
                                size = sizeSelected.value,
                                description = shoeProduct.releaseDate
                            )
                        )
                    },
                ) {
                    cartProduct.remove(
                        CardItem(
                            id = 1,
                            title = shoeProduct.title,
                            price = shoeProduct.marketPriceEnd,
                            quantity = quantity.value,
                            ImageId = shoeProduct.imageUrl,
                            color = colorSelected.value,
                            size = sizeSelected.value,
                            description = shoeProduct.releaseDate
                        )
                    )
                }


            }

        }
    }
}



@ExperimentalAnimationApi
@Composable
fun features(
    quantity: MutableState<Int>,
    isColorDialogVisible: MutableState<Boolean>,
    colorSelected: MutableState<String>,
    isSizeDialogVisible: MutableState<Boolean>,
    sizeSelected: MutableState<Int>,
    isAddedToCart: MutableState<Boolean>,
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, top = 16.dp, bottom = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp, end = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsTitle(
                title = LocalStrings.current.size,
                modifier =Modifier.align(Alignment.CenterHorizontally)
            )
            itemButton(
                modifier = Modifier,
                enabled = !isAddedToCart.value,
                onClicked = {
                    isSizeDialogVisible.value = !isSizeDialogVisible.value
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "${sizeSelected.value}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                            .padding(
                                start = 0.dp,
                                end = 0.dp,
                                top = 0.dp,
                                bottom = 0.dp,
                            )
                    )
                }
            }




        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsTitle(
                title = LocalStrings.current.color,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(end = 0.dp)
            )

            itemButton(
                modifier = Modifier,
                enabled = !isAddedToCart.value,
                onClicked = {
                    isColorDialogVisible.value = !isColorDialogVisible.value
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = when(colorSelected.value){
                            "Black" -> LocalStrings.current.black
                            "Red" -> LocalStrings.current.red
                            "Blue" -> LocalStrings.current.blue
                            "White" -> LocalStrings.current.white
                             "Brown" -> LocalStrings.current.brown
                            else -> LocalStrings.current.black
                          },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                            .padding(
                            start = 0.dp,
                            end = 0.dp,
                            top = 0.dp,
                            bottom = 0.dp,
                        )
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 24.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsTitle(
                title = LocalStrings.current.quantity,
                modifier =Modifier.align(Alignment.CenterHorizontally)
            )
            itemButton(
                modifier = Modifier,
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    IconButton(
                        modifier =Modifier
                            .size(32.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {
                            if (quantity.value > 1)
                            quantity.value -= 1
                        else quantity.value = 1
                                  },
                        enabled = !("${quantity.value}" == "1" || isAddedToCart.value)
                    ) {
                        Icon(imageVector = Icons.Outlined.Remove,
                            contentDescription = LocalStrings.current.minus,
                            tint = if ("${quantity.value}" == "1" || isAddedToCart.value)  Color.Transparent else MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                },
                        )

                    }

                    AnimatedContent(
                        modifier =Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f) ,
                        targetState = "${quantity.value}",
                        transitionSpec = {
                            if (targetState > initialState) {
                                slideInVertically({ height -> height }) + fadeIn() with
                                        slideOutVertically({ height -> -height }) + fadeOut()
                            } else {
                                slideInVertically({ height -> -height }) + fadeIn() with
                                        slideOutVertically({ height -> height }) + fadeOut()
                            }.using(
                                SizeTransform(clip = false)
                            )
                        }
                    ) { targetCount ->
                        Text(
                            text = "$targetCount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    end = 0.dp,
                                )
                        )
                    }


                    IconButton(
                        modifier =Modifier
                            .size(32.dp)
                            .align(Alignment.CenterVertically),
                        onClick = {   quantity.value += 1 },
                        enabled =!(quantity.value >= 9 || isAddedToCart.value)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = LocalStrings.current.add,
                            tint = if (quantity.value >= 9 || isAddedToCart.value)  Color.Transparent else  MaterialTheme.colors.onSurface,
                            modifier = Modifier
                                .size(24.dp)
                                .graphicsLayer {
                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                },
                        )
                    }
                }
            }



        }

    }


}


@Composable
fun itemsTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = "$title:",
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .padding(top = 8.dp, bottom = 8.dp)

    )
}




@OptIn(ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun itemButton(
    modifier: Modifier = Modifier,
    enabled : Boolean = false ,
    onClicked : () -> Unit = {},
    content : @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .background(color = Color.Transparent, shape =RoundedCornerShape(12.dp) )
            .clip(shape =RoundedCornerShape(12.dp))
            .clickable(enabled = enabled){
                onClicked()
            }
            .aspectRatio(1.8f)
            .background(color = Color.Transparent, shape =RoundedCornerShape(12.dp) )
            .clip(shape =RoundedCornerShape(12.dp)),
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface.copy(0.5f)),


    ){


        Column(
            Modifier
                .fillMaxSize()
                .background(
                    color = CardCoverPink.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {}

        content()

    }

}



@Composable
fun payment(
    price: Int,
    quantity: MutableState<Int>,
    isAddedToCart: MutableState<Boolean>,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,

    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 16.dp)
    ) {


        Text(
            color = MaterialTheme.colors.primary,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            fontSize = 22.sp,
            text = LocalStrings.current.detailQuantityValue(quantity.value * price),
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
        )


        Spacer(modifier = Modifier.weight(1f))


        QuantityToggle(
            modifier = Modifier.align(Alignment.CenterVertically),
            isAddedToCart = isAddedToCart,
            onIncrementQuantity = {
                onIncrementQuantity()
            },
            onDecrementQuantity = {
                onDecrementQuantity()
            },
        )

    }


}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
private fun DetailsPreview() {
    val quantity = remember {
        mutableStateOf(1)
    }


    val isColorDialogVisible = remember {
        mutableStateOf(false)
    }

    val isAddedToCart = remember {
        mutableStateOf(false)
    }


    val colorSelected = remember {
        mutableStateOf("Black")
    }

    val isSizeDialogVisible = remember {
        mutableStateOf(false)
    }
    val sizeSelected = remember {
        mutableStateOf(40)
    }

    val cartProduct = remember { Constants.cartList }


    PowerSHTheme {
        ShoeFeatures(
            shoeProduct = Constants.ShoesListPreview[0],
            cartProduct = cartProduct,
            quantity = quantity,
            isColorDialogVisible = isColorDialogVisible,
            colorSelected = colorSelected,
            isSizeDialogVisible = isSizeDialogVisible,
            sizeSelected = sizeSelected,
            isAddedToCart = isAddedToCart,
            onNavigateToCartScreen = {}
        )
    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun ModeNightDetailsPreview() {
    val quantity = remember {
        mutableStateOf(1)
    }


    val isColorDialogVisible = remember {
        mutableStateOf(false)
    }

    val isAddedToCart = remember {
        mutableStateOf(false)
    }


    val colorSelected = remember {
        mutableStateOf("Black")
    }

    val isSizeDialogVisible = remember {
        mutableStateOf(false)
    }
    val sizeSelected = remember {
        mutableStateOf(40)
    }

    val cartProduct = remember { Constants.cartList }

    PowerSHTheme {
        ShoeFeatures(
            shoeProduct = Constants.ShoesListPreview[0],
            cartProduct = cartProduct,
            quantity = quantity,
            isColorDialogVisible = isColorDialogVisible,
            colorSelected = colorSelected,
            isSizeDialogVisible = isSizeDialogVisible,
            sizeSelected = sizeSelected,
            isAddedToCart = isAddedToCart,
            onNavigateToCartScreen = {}
        )
    }
}