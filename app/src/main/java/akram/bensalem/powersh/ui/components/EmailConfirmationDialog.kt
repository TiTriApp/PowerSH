package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.YellowOnboarding
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailConfirmAlertDialog(openDialog: MutableState<Boolean>, onClicked: () -> Unit) {
    if (openDialog.value) {
        AlertDialog(
            modifier = Modifier.padding(16.dp),
            onDismissRequest = {
                onClicked()
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Confirmation",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 16.dp, bottom = 4.dp)
                )
            },
            text = {

                Column() {
                    Text(
                        text = "You have to confirm your email, Please!",
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 16.dp, bottom = 4.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_mail),
                        contentDescription ="confirm",
                        modifier = Modifier
                            .padding(48.dp, 32.dp)
                    )
                }

            },
            confirmButton = {
                Button(
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                        disabledBackgroundColor = CardCoverPink,
                        disabledContentColor = CardCoverPink,
                    ),
                    modifier = Modifier
                        .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp)),
                    onClick = {
                        onClicked()
                        openDialog.value = false

                    }) {
                    Text(
                        text = "Ok",
                        color = YellowOnboarding,
                        style = TextStyle(
                            background = MaterialTheme.colors.primary,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 2.dp,
                            bottom = 2.dp
                        )
                    )
                }
            }
        )
    }
}