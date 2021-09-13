package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.components.UploadImageToggle
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.YellowOnboarding
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
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
fun ccpOption(
    visible: Boolean,
    price: Int,
    modifier: Modifier,
    imageUri: Uri?,
    onUploadImageClicked: () -> Unit = {},
    onRemoveImageClicked: () -> Unit = {},
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX ={ it },
            animationSpec=  spring()
        ),
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
                        painter = painterResource(id = R.drawable.ic_credit_card),
                        contentDescription = "Cash on delivery",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "Pay via CCP or BaridiMob",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Text(
                        text = "Please send the total amount that you will pay to this CCP account:",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "Our CCP account: xxxxxxxxxx xx",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "The total amount: ${price} DA",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "Then send a proof by uploading an image of the transcription here",
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


                    UploadImageToggle(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        isImageSelected = imageUri != null,
                        onAddClicked = {
                            onUploadImageClicked()
                        },
                        onRemoveClicked = {
                            onRemoveImageClicked()
                        },
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                    Text(
                        text = "Dear customer, make sure to keep the proof of payment as you will receive a call from our customer service to confirm your order",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp)
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