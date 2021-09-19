package akram.bensalem.powersh.ui.main.screens.login

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.components.CustomTextField
import akram.bensalem.powersh.ui.components.EmailConfirmAlertDialog
import akram.bensalem.powersh.ui.components.checkYourConectivityAlertDialog
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.*
import akram.bensalem.powersh.utils.authentification.Authenticate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
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
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authentication: MutableState<Authenticate> = remember { mutableStateOf(Authenticate(null)) } ,
    onBackToMainScreen: () -> Unit = {}
) {


    val emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val passwordState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val firstNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val lastNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val repeatPasswordState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val lastNameRequester = remember { FocusRequester() }

    val emailRequester = remember { FocusRequester() }

    val firstNameRequester = remember { FocusRequester() }

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

    val localStrings = LocalStrings.current

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {

            Row(modifier = Modifier) {
                CustomTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                        .background(
                            color = MaterialTheme.colors.surface,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    title = LocalStrings.current.firstName,
                    fieldState = firstNameState,
                    icon = Icons.Outlined.AccountCircle,
                    insideTextColor = MaterialTheme.colors.onBackground,
                    iconTint = MaterialTheme.colors.onBackground,
                    isPassword = false,
                    focusRequester = firstNameRequester,
                    autofillType = AutofillType.PersonFirstName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isValid = isNameValid(name = firstNameState.value.text),
                    errorMessage = LocalStrings.current.tooShort,
                    onNext = {
                        lastNameRequester.requestFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )

                CustomTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                        .background(
                            color = MaterialTheme.colors.surface,
                            RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    title = LocalStrings.current.lastName,
                    fieldState = lastNameState,
                    icon = Icons.Outlined.AccountCircle,
                    insideTextColor = MaterialTheme.colors.onBackground,
                    iconTint = MaterialTheme.colors.onBackground,
                    isPassword = false,
                    focusRequester = lastNameRequester,
                    autofillType = AutofillType.PersonLastName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isValid = isNameValid(name = lastNameState.value.text),
                    errorMessage = LocalStrings.current.tooShort,
                    onNext = {
                        emailRequester.requestFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )
            }




            CustomTextField(
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                textFieldModifier = Modifier
                    .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                title = LocalStrings.current.email,
                fieldState = emailState,
                icon = Icons.Outlined.Email,
                insideTextColor = MaterialTheme.colors.onBackground,
                iconTint = MaterialTheme.colors.onBackground,
                isPassword = false,
                focusRequester = emailRequester,
                autofillType = AutofillType.EmailAddress,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                isValid = isEmailValid(email = emailState.value.text),
                errorMessage = LocalStrings.current.emailIsNotValid,
                onNext = {
                    mPasswordFocusRequester.requestFocus()
                },
                onDone = {
                    view.clearFocus()
                }
            )


            CustomTextField(
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                textFieldModifier = Modifier
                    .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                title = LocalStrings.current.password,
                fieldState = passwordState,
                icon = Icons.Outlined.Password,
                insideTextColor = MaterialTheme.colors.onBackground,
                iconTint = MaterialTheme.colors.onBackground,
                isPassword = true,
                focusRequester = mPasswordFocusRequester,
                autofillType = AutofillType.Password,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                isValid = isValidPasswordFormat(password = passwordState.value.text),
                errorMessage = ErrorMessageOfPassword(password = passwordState.value.text, localString = LocalStrings.current),
                onNext = {
                    mRepeatPasswordFocusRequester.requestFocus()
                },
                onDone = {
                    view.clearFocus()
                }
            )



            CustomTextField(
                modifier = Modifier
                    .padding(0.dp, 8.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                textFieldModifier = Modifier
                    .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colors.surface, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                title = LocalStrings.current.repeatPassword,
                fieldState = repeatPasswordState,
                icon = Icons.Outlined.Password,
                insideTextColor = MaterialTheme.colors.onBackground,
                iconTint = MaterialTheme.colors.onBackground,
                isPassword = true,
                focusRequester = mRepeatPasswordFocusRequester,
                autofillType = AutofillType.Password,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                isValid = isRepeatPasswordValid(
                    password = passwordState.value.text,
                    repeatPassword = repeatPasswordState.value.text
                ),
                errorMessage = LocalStrings.current.repeatPasswordError,
                onNext = {
                    view.clearFocus()
                },
                onDone = {
                    view.clearFocus()
                }
            )

            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

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

                enabled = (isValid(
                            email = emailState.value.text,
                            password = passwordState.value.text,
                            firstName = firstNameState.value.text,
                            lastName = lastNameState.value.text,
                            repeatPassword = repeatPasswordState.value.text
                        )),
                onClick = {

                    isOnline.value = isOnline(context = context)

                    if (isOnline(context = context)) {
                        authentication.value.creatNewUser(
                            emailState.value.text,
                            passwordState.value.text,
                            "${firstNameState.value.text} ${lastNameState.value.text}",
                            isOnProgress,
                            isConfirmationEmailSent,
                            localStrings
                        )
                    }

                })
            {
                Text(
                    text = LocalStrings.current.signUp,
                    textAlign = TextAlign.Center,
                    color =if ( isValid(
                        email = emailState.value.text,
                        password = passwordState.value.text,
                        firstName = firstNameState.value.text,
                        lastName = lastNameState.value.text,
                        repeatPassword = repeatPasswordState.value.text
                    ) ) Color.White else  MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    )
                )
            }

            Spacer(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

        }
        if (isOnProgress.value) CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )

        EmailConfirmAlertDialog(
            openDialog = isConfirmationEmailSent,
            onClicked = onBackToMainScreen
        )
        checkYourConectivityAlertDialog(isOnline)
    }


}


private fun isValid(email : String,
                    password :String,
                    firstName : String,
                    lastName : String,
                    repeatPassword: String ):Boolean {
    return (
            email.isNotEmpty()
        && password.isNotEmpty()
        && firstName.isNotEmpty()
        && lastName.isNotEmpty()
        && isEmailValid(email)
        && isRepeatPasswordValid(
            password = password,
            repeatPassword = repeatPassword
        )
        && isNameValid(firstName)
        && isNameValid(lastName)
    )
}




@ExperimentalAnimationApi
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun SignUpScreenPreview() {

    PowerSHTheme {
        SignUpScreen(
            modifier = Modifier.fillMaxSize(),
        )
    }
}