package akram.bensalem.powersh.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

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
    onNavigateToCartScreen : () -> Unit ,
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
            text = "Payment Details",
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
                    .padding(Dimens.MediumPadding.size)
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
                        targetAlpha =0f
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    CartButton(
                        quantity = quantity.value,
                        price = (quantity.value * shoeProduct.marketPriceStart).toDouble(),
                        onClick = {
                          //  pageState.value = "CART"
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




@Composable
fun features(
    quantity: MutableState<Int>,
    isColorDialogVisible: MutableState<Boolean>,
    colorSelected: MutableState<String>,
    isSizeDialogVisible: MutableState<Boolean>,
    sizeSelected: MutableState<Int>,
    isAddedToCart: MutableState<Boolean>,
){

    Row(        modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 0.dp)
    ) {
        //
        //   var modifier = Modifier.align(Alignment.CenterVertically)
        var modifier = Modifier
        Column(
            modifier = Modifier.height(180.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            itemsTitle(title = "Size", modifier =  modifier)
            itemsTitle(title = "Color" , modifier =  modifier)
            itemsTitle(title = "Quantity", modifier =  modifier)
        }

        Column(
            modifier = Modifier.height(180.dp),
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            itemButton(
                value = "  ${sizeSelected.value}  ",
                modifier=  modifier,
                isColorDialogVisible = isSizeDialogVisible,
                enabled = isAddedToCart,
            )
            itemButton(
                value = colorSelected.value,
                modifier=  modifier,
                isColorDialogVisible = isColorDialogVisible,
                enabled = isAddedToCart
            )
            specialItemButton(
                value = "${quantity.value}",
                modifier =  Modifier,
                enabled = isAddedToCart,
                onAdd = {
                    quantity.value += 1
                },
                onSubstract = {
                    if (quantity.value > 1)
                        quantity.value -= 1
                    else quantity.value = 1
                }
            )
        }

    }
}



@Composable
fun itemsTitle(title: String, modifier: Modifier){
    Text(
        text = "$title:",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold ,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .padding(top = 8.dp, bottom= 8.dp)

    )
}



@Composable
fun itemButton(
    value: String, modifier: Modifier,
    isColorDialogVisible: MutableState<Boolean>,
    enabled: MutableState<Boolean>,
){

    OutlinedButton(
        enabled = !enabled.value,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = CardCoverPink,
            contentColor = Color.LightGray ,
        ),
        shape = CircleShape,
        modifier = modifier
            .padding(start = 48.dp, top = 8.dp, bottom = 8.dp)
            .width(160.dp),
        onClick = {
            isColorDialogVisible.value =! isColorDialogVisible.value
        }) {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal ,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(
                start = 36.dp,
                end =36.dp,
            )
        )
    }

}

@Composable
fun specialItemButton(
    value: String,
    modifier: Modifier = Modifier,
    onAdd: () -> Unit = {},
    onSubstract: () -> Unit = {},
    enabled: MutableState<Boolean>
){

    OutlinedButton(
        enabled = false,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = CardCoverPink,
            contentColor = Color.LightGray ,
            disabledBackgroundColor =if (!enabled.value) CardCoverPink else MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
            disabledContentColor =if (!enabled.value) Color.LightGray else MaterialTheme.colors.onSurface.copy(alpha = 0.12f)  ,
        ),
        shape = CircleShape,
        modifier = modifier
            .padding(start = 48.dp, top = 8.dp, bottom = 8.dp)
            .width(160.dp)


        ,
        onClick = {

        }) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add",
            tint = if (value.toInt() >= 10 || enabled.value) Color.LightGray else Color.DarkGray,
            modifier = Modifier
                .clickable(
                    enabled = if (value.toInt() >= 10 || enabled.value ) false else true,
                ) {
                    onAdd()
                }

        )
        ClickableText(
            text = AnnotatedString(""),
            onClick = {
                if ( value.toInt() <= 10 || !enabled.value)
                    onAdd()
            },
            modifier = Modifier
                .weight(1f)
        )
        Text(
            text = "$value",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(
                start = 0.dp,
                end = 0.dp,
            )
        )

        ClickableText(
            text = AnnotatedString(""),
            onClick = {
                if ( value != "1" || !enabled.value)
                    onSubstract()
            },
            modifier = Modifier
                .weight(1f)
        )
        Icon(imageVector = Icons.Outlined.Remove,
            contentDescription = "Minus",
            tint = if (value.equals("1") || enabled.value) Color.LightGray else Color.DarkGray,
            modifier = Modifier

                .clickable(
                    enabled = if (value.equals("1") || enabled.value ) false else true,
                ) {
                    onSubstract()
                }


        )

    }
}





@Composable
fun payment(
    price: Int,
    quantity: MutableState<Int>,
    isAddedToCart: MutableState<Boolean>,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,

    ){

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
            text = "${quantity.value * price} DA",
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