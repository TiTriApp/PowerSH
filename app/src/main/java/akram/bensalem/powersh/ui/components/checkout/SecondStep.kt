package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.components.CustomTextField
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.utils.*
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SecondStep(
    visible: Boolean,
    fullAddress: MutableState<String>,
    emailState: MutableState<TextFieldValue>,
    phoneState: MutableState<TextFieldValue>,
    lastNameState: MutableState<TextFieldValue>,
    firstNameState: MutableState<TextFieldValue>
) {
    val wilayaList = Constants.wilayaList

    val wilayaSelectedIndex = remember { mutableStateOf(0) }
    val dairaSelectedIndex = remember { mutableStateOf(0) }
    val communeSelectedIndex = remember { mutableStateOf(0) }

    val fullAddressState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val mFullAddressRequester = remember { FocusRequester() }

    val phoneRequester = remember { FocusRequester() }
    val emailRequester = remember { FocusRequester() }
    val firstNameRequester = remember { FocusRequester() }

    val lastNameRequester = remember { FocusRequester() }

    val view = LocalView.current

    val context = LocalContext.current

    val dairaList = getDairaFromWilayaJson(
        context,
        wilayaSelectedIndex.value,
    )
    val communeList = getCommuneFromWilayaJson(
        context,
        wilayaSelectedIndex.value,
        dairaList[dairaSelectedIndex.value]
    )

    if (wilayaSelectedIndex.value != 0 && communeSelectedIndex.value != 0 && dairaSelectedIndex.value != 0) {
        fullAddress.value =
            "${fullAddressState.value.text}, ${communeList[communeSelectedIndex.value]}, ${dairaList[dairaSelectedIndex.value]}, ${wilayaList[wilayaSelectedIndex.value]}"
    }


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = spring()
        ),
        modifier = Modifier.fillMaxSize()
    )
    {

            Card(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, bottom = 48.dp, start = 16.dp, end = 16.dp)
                    .navigationBarsPadding()
            ) {


                Column(
                    Modifier
                        .fillMaxSize()
                        .background(
                            color = CardCoverPink.copy(alpha = 0.23f),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {}



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(state = rememberScrollState())

                ) {

                    Text(
                        text = LocalStrings.current.dearCustomerFillTheForm,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 8.dp,
                            top = 16.dp,
                            bottom = 4.dp
                        )
                    )


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    ) {
                        CustomTextField(
                            modifier = Modifier
                                .padding(0.dp, 8.dp)
                                .weight(1f)
                                .padding(start = Dimens.SmallPadding.size, end = Dimens.SmallPadding.size),
                            textFieldModifier = Modifier
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.onBackground,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(8.dp),
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
                            errorMessage =LocalStrings.current.tooShort,
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
                                .padding(start = Dimens.SmallPadding.size, end = Dimens.SmallPadding.size),
                            textFieldModifier = Modifier
                                .border(
                                    1.dp,
                                    MaterialTheme.colors.onBackground,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(
                                    color = MaterialTheme.colors.surface,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(8.dp),
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
                            .border(
                                1.dp,
                                MaterialTheme.colors.onBackground,
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = MaterialTheme.colors.surface,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(8.dp),
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
                            phoneRequester.requestFocus()
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
                            .border(
                                1.dp,
                                MaterialTheme.colors.onBackground,
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = MaterialTheme.colors.surface,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(8.dp),
                        title = LocalStrings.current.phoneNumber,
                        fieldState = phoneState,
                        focusRequester = phoneRequester,
                        icon = Icons.Outlined.PhoneAndroid,
                        insideTextColor = MaterialTheme.colors.onBackground,
                        iconTint = MaterialTheme.colors.onBackground,
                        isPassword = false,
                        autofillType = AutofillType.PhoneNumber,
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done,
                        isValid = isPhoneValid(phone = phoneState.value.text),
                        errorMessage = LocalStrings.current.phoneNumberIsNotValid,
                        onNext = {
                            mFullAddressRequester.requestFocus()
                        },
                        onDone = {
                            view.clearFocus()
                        }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.SmallPadding.size, 0.dp)
                    ) {
                        wilayaDropDownMenu(
                            modifier = Modifier.weight(1f),
                            wilayaList,
                            wilayaSelectedIndex,
                            dairaSelectedIndex,
                            communeSelectedIndex
                        )
                        diaraDropDownMenu(modifier = Modifier.weight(1f),
                            dairaList,
                            dairaSelectedIndex,
                            communeSelectedIndex)
                        communeDropDownMenu(
                            modifier = Modifier.weight(1f),
                            communeList,
                            communeSelectedIndex)

                    }


                    CustomTextField(
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        textFieldModifier = Modifier
                            .border(
                                1.dp,
                                MaterialTheme.colors.onBackground,
                                RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = MaterialTheme.colors.surface,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(8.dp),
                        title = LocalStrings.current.fullAddress,
                        fieldState = fullAddressState,
                        focusRequester = mFullAddressRequester,
                        icon = Icons.Outlined.Place,
                        insideTextColor = MaterialTheme.colors.onBackground,
                        iconTint = MaterialTheme.colors.onBackground,
                        isPassword = false,
                        autofillType = AutofillType.AddressStreet,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                        isValid = isNameValid(name = fullAddressState.value.text),
                        errorMessage = LocalStrings.current.tooShort,
                        onNext = {
                            view.clearFocus()
                        },
                        onDone = {
                            view.clearFocus()
                        }
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                    )

                }


            }

        }

}