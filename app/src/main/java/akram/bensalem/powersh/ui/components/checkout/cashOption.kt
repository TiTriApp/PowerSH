package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.YellowOnboarding
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun cashOption(visible: Boolean,
               price: Int,
               modifier: Modifier,
               onCallUsClicked : () -> Unit = {},
) {


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize()
    )
    {

        Box(modifier =
        modifier
            .fillMaxSize()
            .padding(top = 0.dp, bottom = 64.dp, start = 16.dp, end = 16.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(12.dp),
            )
        ) {

            Card(
                backgroundColor = Color.White,
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                modifier =
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.ic_wallet),
                        contentDescription = "Cash on delivery",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Cash on delivery",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Text(
                        text = "In the Cash on delivery payment system, you have to fill the form and we will send you your purchases within 2 days",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                    Text(
                        text = "The total amount you will pay is : ${price}DA",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f))

                    Button(
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            disabledBackgroundColor = CardCoverPink,
                            disabledContentColor = CardCoverPink,
                        ),
                        modifier = Modifier
                            .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                            .align(Alignment.CenterHorizontally),
                        onClick = {
                            onCallUsClicked()
                        }) {
                        Text(
                            text ="CALL US",
                            fontSize = 11.sp,
                            color = YellowOnboarding,
                            style = TextStyle(
                                background = MaterialTheme.colors.primary,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 0.dp,
                                bottom = 0.dp
                            )
                        )
                    }


                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f))

                    Text(
                        text = "Dear customer, make sure to fill your information carefully and you will be receive a call from our customer service to confirm your order",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                }
            }


            Column(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .background(
                        color = CardCoverPink.copy(alpha = 0.1f)
                    )
            ) {}


        }






    }
}
