package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHGreen
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.autofill
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@ExperimentalComposeUiApi
@Composable
fun CustomTextField(
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
) {


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
        modifier = modifier

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
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        if (icon != null) {
                            IconButton(
                                onClick = {
                                    isIconButtonPressed = !isIconButtonPressed
                                },
                                modifier = Modifier
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
                                )
                            }

                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart,
                            content = {
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
                        )
                        if (fieldState.value.text.isNotEmpty()) {
                            IconButton(
                                onClick = {},
                                enabled = false,
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.Top)
                            ) {
                                Icon(
                                    imageVector =if (!isValid) Icons.Outlined.ErrorOutline else Icons.Outlined.CheckCircleOutline,
                                    contentDescription = null,
                                    tint = if (!isValid) MaterialTheme.colors.error else PowerSHGreen,
                                    modifier = Modifier.size(24.dp)
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


@ExperimentalComposeUiApi
@Preview
@Composable
fun CustomTextFieldPreview(){
    PowerSHTheme{
        CustomTextField(
            title ="Akram",
        )
    }
}