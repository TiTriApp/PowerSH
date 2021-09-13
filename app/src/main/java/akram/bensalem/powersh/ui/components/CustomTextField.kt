package akram.bensalem.powersh.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akram.bensalem.powersh.ui.screens.login.autofill


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun customTextField(
    modifier: Modifier,
    textFieldModifier: Modifier = Modifier,
    title:  String,
    icon: ImageVector? = null,
    iconTint : Color = Color.DarkGray,
    isPassword : Boolean,
    fieldState: MutableState<TextFieldValue>,
    insideTextColor : Color = Color.DarkGray,
    focusRequester: FocusRequester,
    autofillType: AutofillType,
    singleLine : Boolean = true,
    keyboardType: KeyboardType,
    imeAction : ImeAction = ImeAction.Next,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {}
){



    var isFieldFocus by remember {
        mutableStateOf(false)
    }


    var isIconButtonPressed by remember {
        mutableStateOf(false)
    }

    val color = if (fieldState.value.text.isNotEmpty()) MaterialTheme.colors.primary.copy(0.9f) else Color.LightGray


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
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        if (icon != null) {
                            IconButton(onClick = {
                                isIconButtonPressed = !isIconButtonPressed
                            },
                                modifier = Modifier.size(32.dp).align(Alignment.Top)
                            ) {
                                Icon(
                                    imageVector =if (!isPassword) icon else {if (!isIconButtonPressed) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility},
                                    contentDescription = null,
                                    tint = iconTint,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
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
                                Text(
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 2.dp),
                                    text = fieldState.value.text,
                                    color = insideTextColor
                                )

                            }
                        )
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
                keyboardType =keyboardType
            ),
        )
    }

}
