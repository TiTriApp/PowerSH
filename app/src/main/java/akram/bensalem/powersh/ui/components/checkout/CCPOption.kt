package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.components.UploadImageToggle
import akram.bensalem.powersh.ui.theme.CardCoverPink
import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
            targetOffsetX = { it },
            animationSpec = spring()
        ),
        modifier = Modifier.fillMaxSize()
    )
    {

        Card(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 2.dp,
            shape = RoundedCornerShape(12.dp),
            modifier =
            Modifier
                .fillMaxSize()
                .padding(top = 8.dp, bottom = 48.dp, start = 16.dp, end = 16.dp)
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(state = rememberScrollState())
                    .background(
                        color = CardCoverPink.copy(alpha = 0.23f),
                        shape = RoundedCornerShape(12.dp)
                    )

            ) {}


            Column(
                Modifier
                    .fillMaxSize()
            ) {


                Image(
                    painter = painterResource(id = R.drawable.ic_credit_card),
                    contentDescription = LocalStrings.current.payUsingCCP,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = LocalStrings.current.payViaCCPorBaridiMob,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Text(
                    text = LocalStrings.current.sendTotalAmountPlease,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = LocalStrings.current.ourCCPAccountIs,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = LocalStrings.current.totalAmountValue(price),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                )

                Text(
                    text = LocalStrings.current.sendProof,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )


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

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Text(
                    text = LocalStrings.current.dearCustomerNotify,
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 8.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

            }
        }
    }

}