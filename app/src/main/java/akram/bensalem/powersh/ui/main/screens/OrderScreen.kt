package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.components.orderItemEntry
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onEntryClick: (ShoeProduct) -> Unit = { },
    onInfo: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .navigationBarsPadding()
    ) {

        if (
            orderList.size != 0
        ) {

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(bottom = Dimens.LargePadding.size)
            ) {
                itemsIndexed(orderList) { index, row ->
                    orderItemEntry(
                        order = row,
                        onInfo = { onInfo() },
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                }


            }

        }

        AnimatedVisibility(
            visible = orderList.size == 0,
            enter = fadeIn(initialAlpha = 0f, tween(200)),
            exit = fadeOut(targetAlpha = 0f, tween(200)),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 32.dp, end = 32.dp),
                    painter = painterResource(id = R.drawable.ic_orders),
                    contentDescription = "Empty Orders List"
                )
                Text(
                    color = MaterialTheme.colors.onSurface,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    text = "There are no Orders!",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 0.dp, bottom = 8.dp, top = 16.dp)
                .align(Alignment.BottomCenter)
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
                    text = "Total of Orders",
                    modifier = Modifier
                        .align(Alignment.Start)
                )

                Text(
                    color = MaterialTheme.colors.primary,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    text = "${orderList.size} orders",
                    modifier = Modifier
                        .align(Alignment.Start)
                )

            }
            Spacer(modifier = Modifier.weight(1f))
            if (orderList.size > 0)
                Button(
                    enabled = orderList.size > 0,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (orderList.size > 0) MaterialTheme.colors.primary else CardCoverPink,
                        disabledBackgroundColor = CardCoverPink,
                    ),
                    modifier = Modifier
                        .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                        .align(Alignment.CenterVertically),
                    onClick = {
                        orderList.clear()
                    }) {
                    Text(
                        text = if (orderList.size > 0) "Remove All" else "",
                        color = if (orderList.size > 0) Color.White else Color.DarkGray,
                        style = TextStyle(
                            background = if (orderList.size > 0) MaterialTheme.colors.primary else CardCoverPink,
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
