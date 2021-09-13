package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.ui.main.screens.communeDropDownMenu
import akram.bensalem.powersh.ui.main.screens.customTextField
import akram.bensalem.powersh.ui.main.screens.diaraDropDownMenu
import akram.bensalem.powersh.ui.main.screens.wilayaDropDownMenu
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.getCommuneFromWilayaJson
import akram.bensalem.powersh.utils.getDairaFromWilayaJson
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun secondStep(
    visible: Boolean,
    fullAddress: MutableState<String>,
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

    val mphoneRequester = remember { FocusRequester() }
    val mfirstNameRequester = remember { FocusRequester() }

    val mlastNameRequester = remember { FocusRequester() }

    val view = LocalView.current

    val context = LocalContext.current

    val   dairaList  = getDairaFromWilayaJson(
        context,
        wilayaSelectedIndex.value,
    )
    val   communeList  = getCommuneFromWilayaJson(context, wilayaSelectedIndex.value , dairaList[dairaSelectedIndex.value])

    if (wilayaSelectedIndex.value != 0 && communeSelectedIndex.value != 0 && dairaSelectedIndex.value != 0) {
        fullAddress.value = "${fullAddressState.value.text}, ${communeList[communeSelectedIndex.value]}, ${dairaList[dairaSelectedIndex.value]}, ${wilayaList[wilayaSelectedIndex.value]}"
    }


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX ={ it },
            animationSpec=  spring()
        ),
        modifier = Modifier.fillMaxSize()
    )
    {


        Box(modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 0.dp, bottom = 64.dp, start = 16.dp, end = 16.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(12.dp),
            )
        ) {

            Card(
                backgroundColor = Color.White,
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                modifier =
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(state = rememberScrollState())

                ) {

                    Text(
                        text = "Dear customer, make sure to confirm your delivery information by filling this form:",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 16.dp, bottom = 4.dp)
                    )


                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
                    ){
                        customTextField(
                            modifier= Modifier
                                .weight(1f)
                                .padding(start = 8.dp, end = 8.dp)
                            ,
                            textFieldModifier = Modifier
                                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                                .padding(9.dp),
                            title=  "First Name",
                            fieldState =firstNameState,
                            focusRequester = mfirstNameRequester,
                            autofillType= AutofillType.AddressStreet,
                            keyboardType= KeyboardType.Text,
                            imeAction  = ImeAction.Next,
                            onNext = {
                                mlastNameRequester.requestFocus()
                            },
                            onDone = {}
                        )

                        customTextField(
                            modifier= Modifier
                                .weight(1f)
                                .padding(start = 8.dp, end = 8.dp)
                            ,
                            textFieldModifier = Modifier
                                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                                .padding(9.dp),
                            title=  "Last Name",
                            fieldState =lastNameState,
                            focusRequester = mlastNameRequester,
                            autofillType= AutofillType.AddressStreet,
                            keyboardType= KeyboardType.Text,
                            imeAction  = ImeAction.Next,
                            onNext = {
                                mphoneRequester.requestFocus()
                            },
                            onDone = {}
                        )
                    }

                    customTextField(
                        modifier= Modifier
                            .padding(0.dp, 8.dp)
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp),
                        textFieldModifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        title=  "Phone Number",
                        fieldState =phoneState,
                        focusRequester = mphoneRequester,
                        autofillType= AutofillType.PhoneNumber,
                        keyboardType= KeyboardType.Phone,
                        imeAction  = ImeAction.Done,
                        onNext = {},
                        onDone = {
                            view.clearFocus()
                        }
                    )

                    wilayaDropDownMenu(wilayaList, wilayaSelectedIndex,dairaSelectedIndex ,communeSelectedIndex )
                    diaraDropDownMenu(dairaList, dairaSelectedIndex, communeSelectedIndex)
                    communeDropDownMenu(communeList, communeSelectedIndex )

                    customTextField(
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                        ,
                        textFieldModifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                        ,
                        title=  "Full Address",
                        fieldState =fullAddressState,
                        focusRequester = mFullAddressRequester,
                        autofillType= AutofillType.AddressStreet,
                        keyboardType= KeyboardType.Text,
                        imeAction  = ImeAction.Done,
                        onNext = {},
                        onDone = {
                            view.clearFocus()
                        }
                    )


                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp))

                }



            }

        }





    }
}