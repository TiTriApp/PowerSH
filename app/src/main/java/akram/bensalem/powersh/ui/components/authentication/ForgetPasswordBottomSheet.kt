package akram.bensalem.powersh.ui.components.authentication

import akram.bensalem.powersh.ui.components.CustomTextField
import akram.bensalem.powersh.utils.authentification.Authenticate
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgetPasswordBottomSheet(
    authentication: MutableState<Authenticate> = remember { mutableStateOf(Authenticate(null)) },
) {

    val emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val mEmailRequester = remember { FocusRequester() }

    val view = LocalView.current

    Column(
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {


        Text(
            text = "Reset Password",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(start = 16.dp, top = 36.dp, bottom = 16.dp)
        )


        CustomTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            textFieldModifier = Modifier
                .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = "Email Address",
            fieldState = emailState,
            icon = Icons.Outlined.Email,
            insideTextColor = MaterialTheme.colors.onBackground,
            iconTint = MaterialTheme.colors.onBackground,
            isPassword = false,
            focusRequester = mEmailRequester,
            autofillType = AutofillType.EmailAddress,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            onNext = {
            },
            onDone = {
                view.clearFocus()
            }
        )


        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 56.dp, top = 24.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(18.dp)
                ),
            onClick = {
                authentication.value.reinstalisationDeMotDePass(emailState.value.text)
            }) {
            Text(
                text = "Reset",
                textAlign = TextAlign.Center,
                color = Color.White,
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
