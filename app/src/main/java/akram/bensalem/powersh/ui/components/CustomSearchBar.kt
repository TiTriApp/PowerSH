package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.Shapes
import akram.bensalem.powersh.utils.asAutoCompleteEntities
import akram.bensalem.powersh.utils.autofill
import akram.bensalem.powersh.utils.localization.Locales
import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import java.util.*





@ExperimentalAnimationApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    hint: String = LocalStrings.current.searchByShoesName,
    onSearch: (String) -> Unit = { },
    onOptionsClicked: () -> Unit = { },
    onDismissSearchClicked: () -> Unit = { },
    keyboardController: SoftwareKeyboardController?
    = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager = LocalFocusManager.current
) {


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


    var searchText by remember {
        mutableStateOf("")
    }

    var isHintActive by remember {
        mutableStateOf(hint.isNotEmpty())
    }
    var isTyping by remember {
        mutableStateOf(false)
    }

    val paddingSize: Dp by animateDpAsState(
        targetValue = if (isHintActive) {
            Dimens.SmallPadding.size
        } else Dimens.ElevationPadding.size
    )

    val angle: Float by animateFloatAsState(
        targetValue = if (isHintActive) -90F else 0F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    val searchAndOptionsAngle: Float by animateFloatAsState(
        targetValue = if (isHintActive) 0F else 90F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val hintAlpha: Float by animateFloatAsState(
        targetValue = if (isHintActive) 1F else 0.5f
    )


    val items = listOf(
        "Adidas",
        "Basket",
        "Nike",
        "Versac",
        "Air"
    )
    val autoCompleteEntities = items.asAutoCompleteEntities(
        filter = { item, query ->
            item.lowercase(Locale.getDefault())
                .startsWith(query.lowercase(Locale.getDefault()))
        }
    )


    AutoCompleteBox(
        items = autoCompleteEntities,
        itemContent = { item ->
            ValueAutoCompleteItem(item.value)
        }
    ) {

        onItemSelected { item ->
            searchText = item.value
            filter(searchText)
            onSearch(searchText)
            keyboardController?.hide()
            focusManager.clearFocus()
            isTyping = searchText.isNotBlank()

        }


        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            BoxWithConstraints(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = paddingSize)
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = CircleShape
                    )
            ) {
                ConstraintLayout(alphaAnimatedModifier.fillMaxWidth()) {
                    val (search, field, options) = createRefs()

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .constrainAs(search) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                            }
                    ) {
                        if (isHintActive) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = stringResource(id = R.string.search_icon),
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier
                                    .padding(Dimens.MediumPadding.size)
                                    .rotate(searchAndOptionsAngle)
                                    .graphicsLayer {

                                                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                                },
                            )
                        } else {
                            IconButton(
                                onClick = {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    isTyping = searchText.isNotBlank()
                                },
                                modifier = Modifier
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowBack,
                                    contentDescription = stringResource(id = R.string.back_icon),
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier
                                    .rotate(angle)
                                                                .graphicsLayer {
                                                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                                },
                                )
                            }
                        }

                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .padding(vertical = Dimens.SmallPadding.size)
                            .constrainAs(field) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(search.end)
                                end.linkTo(options.start)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        if (!isTyping) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colors.onSurface
                                    .copy(alpha = hintAlpha),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                        BasicTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                onSearch(it)
                                isTyping = searchText.isNotBlank()
                                filter(searchText)
                            },
                            maxLines = 1,
                            cursorBrush = SolidColor(MaterialTheme.colors.primary),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.subtitle1
                                .copy(color = MaterialTheme.colors.onSurface),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Words,
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide()
                                    if (searchText.isBlank()) {
                                        focusManager.clearFocus()
                                    }
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged {
                                    isHintActive = !it.isFocused
                                    isSearching = it.isFocused
                                }
                        )
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .constrainAs(options) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                    ) {
                        if (!isHintActive) {
                            IconButton(
                                onClick = {
                                    onDismissSearchClicked()
                                    searchText = ""
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    isTyping = searchText.isNotBlank()
                                    filter(searchText)
                                },
                                modifier = Modifier
                                    .padding(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = stringResource(id = R.string.clear_icon),
                                    tint = MaterialTheme.colors.onSurface,
                                    modifier = Modifier.rotate(angle)
                                                                .graphicsLayer {
                                                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                                },
                                )
                            }
                        }
                    }
                }
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = Dimens.ElevationPadding.size)
                    .size(36.dp)
                    .background(MaterialTheme.colors.primary, shape = Shapes.large),

                onClick = {
                    onOptionsClicked()
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            ) {
                Icon(
                    modifier =
                    Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp)
                        .rotate(searchAndOptionsAngle)
                                                    .graphicsLayer {
                                                        rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                    },
                    tint = Color.White,
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(id = R.string.option_icon),
                )
            }

        }


    }


}


@Composable
fun ValueAutoCompleteItem(item: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
    }
}


@ExperimentalComposeUiApi
@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    valueText: MutableState<String> = remember {
        mutableStateOf("")
    },
    title: String = "Email Address",
    hint: String = "Enter Your Email",
    mainIcon: ImageVector = Icons.Outlined.Email,
    isPassword: Boolean = false,
    autofillType: AutofillType = AutofillType.EmailAddress,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onNext: () -> Unit = {},
    onDone: (String) -> Unit = {},
    onDismissSearchClicked: () -> Unit = { },
    keyboardController: SoftwareKeyboardController?
    = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
) {
    var isHintActive by remember {
        mutableStateOf(hint.isNotEmpty())
    }
    var isTyping by remember {
        mutableStateOf(false)
    }

    val paddingSize: Dp by animateDpAsState(
        targetValue = if (isHintActive) {
            Dimens.MediumPadding.size
        } else Dimens.SmallPadding.size
    )

    val angle: Float by animateFloatAsState(
        targetValue = if (isHintActive) -90F else 0F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )
    val searchAndOptionsAngle: Float by animateFloatAsState(
        targetValue = if (isHintActive) 0F else 90F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    val hintAlpha: Float by animateFloatAsState(
        targetValue = if (isHintActive) 1F else 0.5f
    )


    var isFieldFocus by remember {
        mutableStateOf(false)
    }


    var isIconButtonPressed by remember {
        mutableStateOf(false)
    }

    val color =
        if (valueText.value.isNotEmpty()) MaterialTheme.colors.primary.copy(0.9f) else Color.LightGray


    val textColor by animateColorAsState(
        color,
        spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
    )




    Column(
        modifier = modifier.fillMaxWidth(),
    ) {

        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = if (isFieldFocus) MaterialTheme.colors.primary else textColor,
            modifier = Modifier.padding(top = 4.dp, start = Dimens.SmallPadding.size, bottom = 8.dp)
        )

        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = paddingSize)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (search, field, options) = createRefs()

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .constrainAs(search) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                ) {
                    if (isHintActive) {
                        IconButton(
                            onClick = {
                                isIconButtonPressed = !isIconButtonPressed
                            },
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                imageVector = if (!isPassword) mainIcon else {
                                    if (!isIconButtonPressed) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                                },
                                contentDescription = title,
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier
                                    .padding(Dimens.MediumPadding.size)
                                    .rotate(searchAndOptionsAngle)
                                    .size(36.dp)
                                                                .graphicsLayer {
                                                                    rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                                },
                            )
                        }

                    } else {
                        IconButton(
                            onClick = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                isTyping = valueText.value.isNotBlank()
                            },
                            modifier = Modifier
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_icon),
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.rotate(angle)
                                                            .graphicsLayer {
                                                                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                            },
                            )
                        }
                    }

                }

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .padding(vertical = Dimens.MediumPadding.size)
                        .constrainAs(field) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(search.end)
                            end.linkTo(options.start)
                            width = Dimension.fillToConstraints
                        }
                ) {
                    if (!isTyping) {
                        Text(
                            text = hint,
                            color = MaterialTheme.colors.onSurface
                                .copy(alpha = hintAlpha),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                    BasicTextField(
                        value = valueText.value,
                        onValueChange = {
                            valueText.value = it
                            onDone(it)
                            isTyping = valueText.value.isNotBlank()
                        },
                        visualTransformation = if (isIconButtonPressed || !isPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        maxLines = 1,
                        cursorBrush = SolidColor(MaterialTheme.colors.primary),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.subtitle1
                            .copy(color = MaterialTheme.colors.onSurface),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = imeAction,
                            keyboardType = keyboardType
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()
                                if (valueText.value.isBlank()) {
                                    focusManager.clearFocus()
                                }
                            },
                            onNext = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onNext()
                            },
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                onDone(valueText.value)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                isHintActive = !it.isFocused
                                isFieldFocus = it.isFocused
                            }
                            .autofill(
                                autofillTypes = listOf(autofillType),
                                onFill = { valueText.value = it },
                            )
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .constrainAs(options) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    if (!isHintActive) {
                        IconButton(
                            onClick = {
                                onDismissSearchClicked()
                                valueText.value = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                isTyping = valueText.value.isNotBlank()
                            },
                            modifier = Modifier
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(id = R.string.clear_icon),
                                tint = MaterialTheme.colors.onSurface,
                                modifier = Modifier.rotate(angle)
                                                            .graphicsLayer {
                                                                rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                            },
                            )
                        }
                    }
                }
            }
        }
    }


}

@ExperimentalComposeUiApi
@Preview
@Composable
private fun CustomEditTextPreview() {
    PowerSHTheme {
        CustomEditText()
    }
}



@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CustomEditTextNightPreview() {
    PowerSHTheme {
        CustomEditText()
    }
}


@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Preview
@Composable
private fun CustomSearchBarPreview() {
    PowerSHTheme {
        CustomSearchBar()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun CustomSearchBarNightPreview() {
    PowerSHTheme {
        CustomSearchBar()
    }
}