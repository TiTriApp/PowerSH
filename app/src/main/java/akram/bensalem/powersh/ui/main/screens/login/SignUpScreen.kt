package com.akram.bensalem.powersh.ui.screens.login

import akram.bensalem.powersh.ui.components.EmailConfirmAlertDialog
import akram.bensalem.powersh.ui.components.checkYourConectivityAlertDialog
import akram.bensalem.powersh.ui.components.customTextField
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authentifier
import akram.bensalem.powersh.utils.isOnline
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun signUpScreen(
    scrollState : ScrollState = rememberScrollState(),
    modifier: Modifier = Modifier,
    fireBaseAuthentification: Authentifier,
    onBackToMainScreen : () -> Unit = {}
){



    var emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    var passwordState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var firstNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var lastNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var repeatPasswordState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val mlastNameRequester = remember { FocusRequester() }

    val mEmailRequester = remember { FocusRequester() }

    val mfirstNameRequester = remember { FocusRequester() }

    val view = LocalView.current
    val context = LocalContext.current
    val mPasswordFocusRequester = remember { FocusRequester() }
    val mRepeatPasswordFocusRequester = remember { FocusRequester() }


    val isOnProgress = remember {
        mutableStateOf(false)
    }

    val isConfirmationEmailSent = remember {
        mutableStateOf(false)
    }
    val isOnline = remember {
        mutableStateOf(true)
    }

Box(
    modifier = modifier
        .fillMaxSize()
        .background(Color.White)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .background(Color.White)

    ) {

        Row(modifier = Modifier) {
            customTextField(
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp),
                textFieldModifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                title = "First Name",
                fieldState = firstNameState,
                icon = Icons.Outlined.AccountCircle,
                isPassword = false,
                focusRequester = mfirstNameRequester,
                autofillType = AutofillType.PersonFirstName,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                onNext = {
                    mlastNameRequester.requestFocus()
                },
                onDone = {
                    view.clearFocus()
                }
            )

            customTextField(
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .weight(1f)
                    .padding(start = 16.dp, end = 16.dp),
                textFieldModifier = Modifier
                    .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                title = "Last Name",
                fieldState = lastNameState,
                icon = Icons.Outlined.AccountCircle,
                isPassword = false,
                focusRequester = mlastNameRequester,
                autofillType = AutofillType.PersonLastName,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                onNext = {
                    mEmailRequester.requestFocus()
                },
                onDone = {
                    view.clearFocus()
                }
            )
        }




        customTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = "Email Address",
            fieldState = emailState,
            icon = Icons.Outlined.Email,
            isPassword = false,
            focusRequester = mEmailRequester,
            autofillType = AutofillType.EmailAddress,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            onNext = {
                mPasswordFocusRequester.requestFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )


        customTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = "Password",
            fieldState = passwordState,
            icon = Icons.Outlined.Password,
            isPassword = true,
            focusRequester = mPasswordFocusRequester,
            autofillType = AutofillType.Password,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            onNext = {
                mRepeatPasswordFocusRequester.requestFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )



        customTextField(
            modifier = Modifier
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title = "Repeat Password",
            fieldState = repeatPasswordState,
            icon = Icons.Outlined.Password,
            isPassword = true,
            focusRequester = mRepeatPasswordFocusRequester,
            autofillType = AutofillType.Password,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onNext = {
                view.clearFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )

        Spacer(modifier = modifier
            .fillMaxWidth()
            .weight(1f))

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .padding(16.dp, 24.dp)
                .align(Alignment.CenterHorizontally)
                .background(color = MaterialTheme.colors.primary, shape = CircleShape),
            onClick = {

                isOnline.value = isOnline(context = context)

                if (isOnline(context = context)) {
                    var msg = ""
                    if (emailState.value.text.isEmpty() || passwordState.value.text.isEmpty() || firstNameState.value.text.isEmpty() || lastNameState.value.text.isEmpty())
                    {
                        msg = "One of filed or many are empty"
                        Toast.makeText(
                            context,
                            msg,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else {
                        if (passwordState.value.text == repeatPasswordState.value.text) {
                            fireBaseAuthentification.creatNewUser(
                                emailState.value.text,
                                passwordState.value.text,
                                "${firstNameState.value.text} ${lastNameState.value.text}",
                                isOnProgress,
                                isConfirmationEmailSent
                            )

                        } else {
                            Toast.makeText(
                                context,
                                "the passwords are not the same \n please verify your password",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                }

            })
        {
            Text(
                text = "Sign Up",
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

        Spacer(modifier = modifier
            .fillMaxWidth()
            .weight(1f))

    }
if (isOnProgress.value) CircularProgressIndicator(
    modifier = Modifier.align(Alignment.Center)
)

    EmailConfirmAlertDialog(openDialog = isConfirmationEmailSent, onClicked = onBackToMainScreen)
    checkYourConectivityAlertDialog(isOnline)
}


}



@ExperimentalAnimationApi
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun signUpScreenPreview(){
    var authentification : Authentifier = Authentifier(null)

    PowerSHTheme() {
       signUpScreen(
           modifier = Modifier.fillMaxSize(),
           fireBaseAuthentification = authentification
       )
   }
}