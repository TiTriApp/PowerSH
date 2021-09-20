package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.orderItemEntry
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.getCurrentDate
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding


@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun orderScreen(
    orderList: MutableList<OrderItem>,
    onEntryClick: (OrderItem) -> Unit = { },
    onPrinted: () -> Unit

) {


    val isBillingPrintedVisible =  remember {
        mutableStateOf(false)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {


        var example = mutableListOf<OrderItem>(
            OrderItem(
                productList = Constants.cartList2,
                date = "jkkjb"
            ),

        )


        if (
            example.size != 0
        ) {

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        top = 16.dp,
                        bottom =if (!isBillingPrintedVisible.value) 0.dp else Dimens.ExtraLargePadding.size,
                        start = 16.dp,
                        end = 16.dp
                    )

            ) {
                itemsIndexed(example) { _, row ->
                    orderItemEntry(
                        order = row,
                        onPrint = {
                            isBillingPrintedVisible.value = true
                            onPrinted()
                                 },
                        onClickEntry = {
                            isBillingPrintedVisible.value = false
                            onEntryClick(row)
                        },
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }


            }

        }

        AnimatedVisibility(
            visible = example.size == 0,
            enter = fadeIn(initialAlpha = 0f, tween(200)),
            exit = fadeOut(targetAlpha = 0f, tween(200)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Column(
            ) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 32.dp, end = 32.dp)
                        .graphicsLayer {
                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                        },
                    painter = painterResource(id = R.drawable.ic_orders),
                    contentDescription = LocalStrings.current.emptyOrderList,
                    alpha = 0.80F,
                )
                Text(
                    color = MaterialTheme.colors.onSurface,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    text = LocalStrings.current.noOrders,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        AnimatedVisibility(visible = isBillingPrintedVisible.value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp, start = 24.dp, end = 16.dp)
            .align(Alignment.BottomCenter)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                ) {
                    Text(
                        color = MaterialTheme.colors.onBackground,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        text = LocalStrings.current.billingHaveBeenPrinted,
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Text(
                        color = MaterialTheme.colors.primary,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        text = LocalStrings.current.at(getCurrentDate(LocalStrings.current)),
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                if (isBillingPrintedVisible.value)
                    Button(
                        enabled = isBillingPrintedVisible.value,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isBillingPrintedVisible.value) MaterialTheme.colors.primary else CardCoverPink,
                            disabledBackgroundColor = CardCoverPink,
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        onClick = {
                            //
                        }) {
                        Text(
                            text =if( isBillingPrintedVisible.value ) LocalStrings.current.open else "",
                            color = if( isBillingPrintedVisible.value )  Color.White else Color.DarkGray,
                            style = TextStyle(
                                background = if (isBillingPrintedVisible.value) MaterialTheme.colors.primary else CardCoverPink,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 24.dp,
                                end = 24.dp,
                                top = 6.dp,
                                bottom = 6.dp
                            )
                        )
                    }


            }
        }



    }

}
