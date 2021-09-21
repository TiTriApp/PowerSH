package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.confirmMessageSentAlertDialog
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHGreen
import akram.bensalem.powersh.ui.theme.PowerSHLightRed
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.*
import akram.bensalem.powersh.utils.email.Mailer
import akram.bensalem.powersh.utils.localization.Locales
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


private enum class SendModeState { FILL, EMPTY }


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun contactScreen() {



    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = Modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )




    val alphaAnimatedProgress = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = Unit) {
        alphaAnimatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }


    val bottomAnimatedModifier = Modifier
        .graphicsLayer(
            alpha = alphaAnimatedProgress.value,
        )



    val emailState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val nameState = remember {
        mutableStateOf(TextFieldValue(""))
    }


    val messageState = remember {
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


    val localStrings = LocalStrings.current



    val transition = updateTransition(
        if (isValid(
            email = emailState.value.text,
            message =  messageState.value.text,
            fullName = nameState.value.text )) SendModeState.FILL else SendModeState.EMPTY,
        label = ""
    )


    val backgroundColor by transition.animateColor { state ->
        when (state) {
            SendModeState.FILL -> MaterialTheme.colors.primary
            SendModeState.EMPTY -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            SendModeState.FILL -> MaterialTheme.colors.primary
            SendModeState.EMPTY -> LocalContentColor.current
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()

    ) {

        val (card, send, progress) = createRefs()

        Card(
            shape = RoundedCornerShape(14.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 3.dp,
            modifier = animatedModifier
                .fillMaxSize()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(send.top)

                }
                .padding(
                    top = Dimens.UpperMediumPadding.size,
                    bottom = Dimens.MediumPadding.size,
                    start = Dimens.UpperMediumPadding.size,
                    end = Dimens.UpperMediumPadding.size,
                )

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {


                Text(
                    text = LocalStrings.current.entrerEnContact,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SmallPadding.size),
                    textAlign = TextAlign.Center

                )


                akram.bensalem.powersh.ui.components.CustomTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    title = LocalStrings.current.yourFullName ,
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = nameState,
                    icon = Icons.Outlined.AccountCircle,
                    iconTint = MaterialTheme.colors.primary,
                    isPassword = false,
                    focusRequester = nameRequester,
                    autofillType = AutofillType.PersonFullName,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isValid = isFullNameValid(name = nameState.value.text),
                    errorMessage = LocalStrings.current.tooShort,
                    onNext = {
                        emailRequester.requestFocus()
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )


                akram.bensalem.powersh.ui.components.CustomTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    title = LocalStrings.current.yourEmail,
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = emailState,
                    icon = Icons.Outlined.Email,
                    iconTint = MaterialTheme.colors.primary,
                    isPassword = false,
                    isValid = isEmailValid(email = emailState.value.text),
                    errorMessage = LocalStrings.current.emailIsNotValid,
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

                messageTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(12.dp))
                        .background(color = PowerSHLightRed, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    title = LocalStrings.current.yourMessage,
                    insideTextColor = MaterialTheme.colors.onBackground,
                    fieldState = messageState,
                    icon = null,
                    iconTint = MaterialTheme.colors.primary,
                    singleLine = false,
                    isPassword = false,
                    focusRequester = messageRequester,
                    isValid = isNameValid(name = messageState.value.text, 16),
                    errorMessage = LocalStrings.current.tooShort,
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
                
                Spacer(modifier = Modifier.height(16.dp))
                
            }
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = backgroundColor,
                contentColor = contentColor,
                disabledBackgroundColor = backgroundColor,
                disabledContentColor = contentColor
            ),
            shape = CircleShape,
            modifier = bottomAnimatedModifier
                .constrainAs(send) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(card.bottom)
                }
                .padding(
                    bottom =  24.dp,
                    top = 24.dp
                ),
            enabled = isValid(
                email = emailState.value.text,
               message =  messageState.value.text,
                fullName = nameState.value.text ),
            onClick = {
                isOnline.value = isOnline(context = context)
                if (isOnline.value) {

                    isOnProgress.value = true

                    Mailer.sendMail(
                        "powersshoes2@gmail.com",
                        nameState.value.text,
                        emailState.value.text +"\n" + messageState.value.text)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                isOnProgress.value = false
                                isMessageSent.value = true
                            },
                            {
                                isOnProgress.value = false
                                Toast.makeText(context, localStrings.contactFailed, Toast.LENGTH_SHORT).show()
                            }
                                )


                } else if (isOnline.value) {
                    Toast.makeText(context, localStrings.oneFieldOrManyEmpty, Toast.LENGTH_SHORT)
                        .show()
                }


            }) {
            Text(
                text = LocalStrings.current.send,
                textAlign = TextAlign.Center,
                color = if  (isValid(
                    email = emailState.value.text,
                    message =  messageState.value.text,
                    fullName = nameState.value.text )) Color.White else  MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 4.dp,
                    bottom = 4.dp
                )
            )
        }


        if (isOnProgress.value) CircularProgressIndicator(
            modifier = Modifier.constrainAs(progress) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

            }
        )

        confirmMessageSentAlertDialog(isMessageSent, nameState, emailState, messageState)

    }


}


fun sendEmail(
    context: Context,
    recipient: String,
    subject: String,
    message: String,
    title: String = "Send Email",
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
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }

}




private fun isValid(email : String,
                    fullName :String,
                    message: String ):Boolean {
    return (
                    email.isNotEmpty()
                    && fullName.isNotEmpty()
                    && message.isNotEmpty()
                    && isEmailValid(email)
                    && isFullNameValid(fullName)
                    && isNameValid(message, 16)
            )
}








@ExperimentalComposeUiApi
@Composable
fun messageTextField(
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    title: String,
    icon: ImageVector? = null,
    iconTint: Color = Color.DarkGray,
    isPassword: Boolean =false,
    fieldState: MutableState<TextFieldValue> = remember {
        mutableStateOf(TextFieldValue())
    },
    insideTextColor: Color = Color.DarkGray,
    focusRequester: FocusRequester = FocusRequester(),
    autofillType: AutofillType = AutofillType.EmailAddress,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isValid: Boolean = true,
    errorMessage : String = "Error: You input is not valid",
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
){


    //Scale animation
    val animatedProgress = remember {
        Animatable(initialValue = 0.7f)
    }
    LaunchedEffect(key1 = Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }

    val animatedModifier = modifier
        .graphicsLayer(
            scaleX = animatedProgress.value,
            scaleY = animatedProgress.value
        )


    val alphaAnimatedProgress = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = Unit) {
        alphaAnimatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        )
    }


    val alphaAnimatedModifier = Modifier
        .graphicsLayer(
            alpha = alphaAnimatedProgress.value,
        )



    var isFieldFocus by remember {
        mutableStateOf(false)
    }

    var isIconButtonPressed by remember {
        mutableStateOf(false)
    }

    val color =
        if (fieldState.value.text.isNotEmpty()) MaterialTheme.colors.primary.copy(0.9f) else Color.LightGray


    val textColor by animateColorAsState(
        color,
        spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
    )


    Column(
        modifier = animatedModifier

    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = if (isFieldFocus) MaterialTheme.colors.primary else textColor,
            modifier = Modifier.padding(top = 4.dp, start = 2.dp, bottom = 8.dp)
        )
        BasicTextField(
            modifier = textFieldModifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .autofill(
                    autofillTypes = listOf(autofillType),
                    onFill = { fieldState.value = TextFieldValue(it) },
                )
                .onFocusChanged {
                    isFieldFocus = it.isFocused
                },
            singleLine = singleLine,
            textStyle = TextStyle(
                color = insideTextColor
            ),
            cursorBrush = SolidColor(insideTextColor),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    content = {
                        if (icon != null) {
                            IconButton(
                                onClick = {
                                    isIconButtonPressed = !isIconButtonPressed
                                },
                                modifier = alphaAnimatedModifier
                                    .size(32.dp)
                                    .align(Alignment.Top)
                            ) {
                                Icon(
                                    imageVector = if (!isPassword) icon else {
                                        if (!isIconButtonPressed) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                                    },
                                    contentDescription = null,
                                    tint = iconTint,
                                    modifier = Modifier.size(24.dp)
                                                                .graphicsLayer {
                                                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                                },
                                )
                            }

                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart,
                            content = {
                                Box(
                                    modifier.padding(1.dp)
                                ){
                                    if (fieldState.value.text.isEmpty()) {
                                        Text(
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 2.dp),
                                            text = fieldState.value.text,
                                            color = insideTextColor
                                        )
                                    }
                                    innerTextField()
                                }

                            }
                        )
                        if (fieldState.value.text.isNotEmpty()) {
                            IconButton(
                                onClick = {},
                                enabled = false,
                                modifier = alphaAnimatedModifier
                                    .size(32.dp)
                                    .align(Alignment.Top)
                            ) {
                                Icon(
                                    imageVector =if (!isValid) Icons.Outlined.ErrorOutline else Icons.Outlined.CheckCircleOutline,
                                    contentDescription = null,
                                    tint = if (!isValid) MaterialTheme.colors.error else PowerSHGreen,
                                    modifier = Modifier.size(24.dp),
                                )
                            }

                        }
                    }
                )
            },
            keyboardActions = KeyboardActions(
                onNext = {
                    onNext()
                },
                onDone = {
                    onDone()
                }
            ),
            visualTransformation = if (isIconButtonPressed || !isPassword) VisualTransformation.None else PasswordVisualTransformation(),
            value = fieldState.value,
            onValueChange = {
                fieldState.value = it
            },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = keyboardType
            ),
        )

        if (!isValid) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = Dimens.SmallPadding.size)
            )
        }

    }


}

@Preview
@Composable
fun contactScreenPreview() {
    PowerSHTheme {
        contactScreen()
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun contactScreenNightPreview() {
    PowerSHTheme {
        contactScreen()
    }
}