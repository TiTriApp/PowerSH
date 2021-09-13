package com.akram.bensalem.powersh.ui.screens.login

import akram.bensalem.powersh.ui.components.checkYourConectivityAlertDialog
import akram.bensalem.powersh.ui.components.customTextField
import akram.bensalem.powersh.utils.authentification.Authentifier
import akram.bensalem.powersh.utils.isOnline
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    bottomSheetScaffoldState: ModalBottomSheetState,
    fireBaseAuthentification: Authentifier,
    onLoggin: () -> Unit
){

    val context = LocalContext.current

    var emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var passwordState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    var isForgetButtonPressed by remember {
        mutableStateOf(false)
    }


    val view = LocalView.current

    val mEmailRequester = remember { FocusRequester() }

    val mPasswordFocusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()

    val isOnProgress = remember {
        mutableStateOf(false)
    }

    val isLogged =remember {
        mutableStateOf(false)
    }

    val isOnline = remember {
        mutableStateOf(true)
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .background(Color.White)

    ) {

        val (email, password, forget, button, progress) = createRefs()


        customTextField(
            modifier= Modifier
                .constrainAs(email) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title=  "Email Address",
            fieldState =emailState,
            icon =  Icons.Outlined.Email,
            isPassword = false,
            focusRequester = mEmailRequester,
            autofillType= AutofillType.EmailAddress,
            keyboardType= KeyboardType.Email,
            imeAction  = ImeAction.Next,
            onNext = {
                mPasswordFocusRequester.requestFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )

        customTextField(
            modifier= Modifier
                .constrainAs(password) {
                    top.linkTo(email.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(0.dp, 8.dp)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            textFieldModifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(12.dp),
            title=  "Password",
            fieldState =passwordState,
            icon =  Icons.Outlined.Password,
            isPassword = true,
            focusRequester = mPasswordFocusRequester,
            autofillType= AutofillType.Password,
            keyboardType= KeyboardType.Password,
            imeAction  = ImeAction.Done,
            onNext = {
                view.clearFocus()
            },
            onDone = {
                view.clearFocus()
            }
        )


        ClickableText(
            modifier = Modifier
                .constrainAs(forget) {
                    top.linkTo(password.bottom, margin = 16.dp)
                    bottom.linkTo(button.top, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(top = 48.dp, bottom = 8.dp)

                .padding(4.dp),
            text = AnnotatedString("Forget Password?"),
            onClick = {
                isForgetButtonPressed = true
            },
            style = TextStyle(
                color = if (isForgetButtonPressed) MaterialTheme.colors.primary else Color.LightGray,

                ),
        )


        if (isForgetButtonPressed) {
            coroutineScope.launch {
                if (!bottomSheetScaffoldState.isVisible) {
                    bottomSheetScaffoldState.show()
                } else {
                    bottomSheetScaffoldState.hide()
                }

                isForgetButtonPressed = false
            }

        }


        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.primary,
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .constrainAs(button) {
                    top.linkTo(forget.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .background(color = MaterialTheme.colors.primary, shape = CircleShape),
            onClick = {
                isOnline.value = isOnline(context = context)

                if (isOnline.value && emailState.value.text.isNotEmpty() && passwordState.value.text.isNotEmpty()) {
                    fireBaseAuthentification.signIn(
                        emailState.value.text,
                        passwordState.value.text,
                        isOnProgress,
                        isLogged
                    )
                } else if (isOnline.value){
                    var msg = ""
                    if (emailState.value.text.isEmpty() && passwordState.value.text.isEmpty())
                        msg = "Your email and password are empty"
                    else if(emailState.value.text.isEmpty()) msg = "Your email is empty"
                    else msg = "Your password is empty"
                    Toast.makeText(context, msg , Toast.LENGTH_SHORT).show()

                }


            }) {
            Text(
                text = "Login",
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
            modifier = Modifier .constrainAs(progress) {
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)

            })

        if (isLogged.value){
            onLoggin()
        }

        checkYourConectivityAlertDialog(isOnline)



    }

}