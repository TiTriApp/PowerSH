package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.ui.components.checkout.factureText
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.utils.Constants
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi

@ExperimentalMaterialApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ordersList(
    cartProduct: MutableList<OrderItem>,
    onClickEntry: () -> Unit,
    onInfo: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(start = 0.dp, end = 0.dp),
        contentPadding = PaddingValues(
            top = 2.dp,
            bottom = 2.dp
        )
    ) {

        itemsIndexed(cartProduct) { index, row ->
            orderItemEntry(
                order = row,
                onInfo = { onInfo() },
                modifier = Modifier
                    .padding(
                        end = 0.dp,
                    )
            )

            Spacer(modifier = Modifier.padding(4.dp))

        }
    }


}


@ExperimentalMaterialApi
@OptIn(ExperimentalCoilApi::class)
@Composable
fun orderItemEntry(
    modifier: Modifier = Modifier,
    order: OrderItem,
    onClickEntry: () -> Unit = {},
    onInfo: () -> Unit = {},
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


    Card(
        shape = Shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = Dimens.ElevationPadding.size,
        modifier = animatedModifier
            .fillMaxWidth()
            .clip(Shapes.large),
        onClick = {
            onClickEntry()
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.ZeroPadding.size, horizontal = Dimens.SmallPadding.size)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            top = Dimens.MediumPadding.size,
                            bottom = Dimens.MediumPadding.size,
                            end = Dimens.MediumPadding.size,
                            start = Dimens.MediumPadding.size,
                        )
                ) {

                    factureText(
                        title = "ID:",
                        detail = "22154854",
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,
                    )
                    factureText(
                        title = "Date:",
                        detail = order.date,
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,

                        )
                    factureText(
                        title = "Total:",
                        detail = order.total.toString() + " DA",
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,

                        )
                    factureText(
                        title = "Status:",
                        detail = order.status.name,
                        titleColor = MaterialTheme.colors.onBackground,
                        detailColor = MaterialTheme.colors.onSurface,
                    )
                }


                IconButton(
                    onClick = {
                        onInfo()
                    },

                    modifier = Modifier
                        .background(shape = CircleShape, color = Color.Transparent)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "info about order",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically)
                    )
                }

            }

            factureText(
                title = "Payment:",
                detail = order.payment,
                titleColor = MaterialTheme.colors.onBackground,
                detailColor = MaterialTheme.colors.onSurface,
            )

            factureText(
                title = "Address:",
                detail = order.Address,
                titleColor = MaterialTheme.colors.onBackground,
                detailColor = MaterialTheme.colors.onSurface,
            )


            Text(
                text = "Products:",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp, bottom = 8.dp)
            )


            Log.d("AkramBensalem", "order.productList size id ${order.productList}")

            order.productList.forEach { row ->
                finalCartItem(product = row) {}
                Spacer(modifier = Modifier.padding(Dimens.LargePadding.size))
            }


        }


    }

}


@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun orderItemEntryPreview() {
    PowerSHTheme {
        orderItemEntry(
            modifier = Modifier,
            order = OrderItem(
                productList = Constants.cartList1
            ),
        )
    }
}