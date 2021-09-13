package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.ui.components.confirmMessageSentAlertDialog
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHLightRed
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.isOnline
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import androidx.compose.runtime.MutableState


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun contactScreen(){


    var emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    var nameState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    var messageState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val nameRequester = remember { FocusRequester() }

    val emailRequester = remember { FocusRequester() }

    val messageRequester = remember { FocusRequester() }

    val view = LocalView.current
    val context = LocalContext.current


    val isOnProgress = remember {
        mutableStateOf(false)
    }

    val isMessageSent = remember {
        mutableStateOf(false)
    }
    val isOnline = remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        Card(
            shape = RoundedCornerShape(14.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 3.dp,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(
                    bottom = 64.dp,
                    start = Dimens.UpperMediumPadding.size,
                    end = Dimens.UpperMediumPadding.size
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {


                Text(
                    text = "Contact Us",
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SmallPadding.size),
                    textAlign = TextAlign.Center

                )


                akram.bensalem.powersh.ui.components.customTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    title = "Your Full Name",
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = nameState,
                    icon = Icons.Outlined.AccountCircle,
                    iconTint = MaterialTheme.colors.primary,
                    isPassword = false,
                    focusRequester = nameRequester,
                    autofillType = AutofillType.PersonFullName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onNext = {
                        emailRequester.requestFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )


                akram.bensalem.powersh.ui.components.customTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    title = "Your Email",
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = emailState,
                    icon = Icons.Outlined.Email,
                    iconTint = MaterialTheme.colors.primary,
                    isPassword = false,
                    focusRequester = emailRequester,
                    autofillType = AutofillType.EmailAddress,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    onNext = {
                        messageRequester.requestFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )

                akram.bensalem.powersh.ui.components.customTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(96.dp),
                    title = "Your Message",
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = messageState,
                    icon = null,
                    iconTint = MaterialTheme.colors.primary,
                    singleLine = false,
                    isPassword = false,
                    focusRequester = messageRequester,
                    autofillType = AutofillType.NewPassword,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                    onNext = {
                        view.clearFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
            ),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp),
            onClick = {
                isOnline.value = isOnline(context = context)
                if (isOnline.value && emailState.value.text.isNotEmpty() && messageState.value.text.isNotEmpty() && nameState.value.text.isNotEmpty()) {
                   // send message
//      isMessageSent.value = true
                    sendEmail(
                        context = context,
                        recipient = "powersshoes2@gmail.com",
                        subject=  nameState.value.text,
                        message= "Email is : ${emailState.value.text} + ${messageState.value.text}",
                        isMessageSent = isMessageSent
                    )

/*

                    val sender = GMailSender(
                        "arslimane32@gmail.com",
                        "123456789Sl"
                    )

                    sender.sendMail("arslimane32@gmail.com", "arslimane32@gmail.com", "arslimane32@gmail.com", "powersshoes2@gmail.com")
*/


                } else if (isOnline.value){
                    Toast.makeText(context, "One of filed or many are empty" , Toast.LENGTH_SHORT).show()
                }


            }) {
            Text(
                text = "Send",
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }


        if (isOnProgress.value) CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )

        confirmMessageSentAlertDialog(isMessageSent, nameState, emailState, messageState)

    }



}







fun sendEmail(
    context: Context,
    recipient: String,
    subject: String,
    message: String,
    title : String = "Send Email",
    isMessageSent: MutableState<Boolean>
) {
    val mIntent = Intent(Intent.ACTION_SEND)
    mIntent.data = Uri.parse("mailto:")
    mIntent.type = "text/plain"
    mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
    mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    mIntent.putExtra(Intent.EXTRA_TEXT, message)

    try {
        //start email intent
        context.startActivity(Intent.createChooser(mIntent, title))
        isMessageSent.value = true
    }
    catch (e: Exception){
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

}




@Preview
@Composable
fun contactScreenPreview(){
    PowerSHTheme() {
        contactScreen()
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun contactScreenNightPreview(){
    PowerSHTheme() {
        contactScreen()
    }
}