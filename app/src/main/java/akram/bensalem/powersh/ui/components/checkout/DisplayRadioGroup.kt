package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.ui.theme.CardCoverPink
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DisplayRadioGroup(selected: MutableState<String>) {




        Card(
            elevation = 2.dp,
            backgroundColor = MaterialTheme.colors.background,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
        ) {

            Box(modifier = Modifier
                .fillMaxWidth()
            ){

                Column(
                    Modifier
                        .matchParentSize()
                        .background(
                            color = CardCoverPink.copy(alpha = 0.2f),
                            shape= RoundedCornerShape(12.dp),
                        )
                ) {}

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                radioPaymentOption(
                    selected = selected,
                    id = MainPayOptions.CASH_OPTION,
                    title = "Cash on Delivery",
                    detail = "Pay when the products are delivered to you",
                    contentDescription = "Cash on Delivery icon",
                    icon = Icons.Outlined.Money,
                )

                radioPaymentOption(
                    selected = selected,
                    id = MainPayOptions.CCP_OPTION,
                    title = "CCP or BaridiMob",
                    detail = "Pay now using your CCP or BaridiMob",
                    contentDescription = "CCP or BaridiMob icon",
                    icon = Icons.Outlined.CreditCard,
                )
            }


        }

    }



}