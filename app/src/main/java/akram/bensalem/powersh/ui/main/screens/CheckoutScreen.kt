package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.Step
import akram.bensalem.powersh.ui.components.ConfirmAlertDialog
import akram.bensalem.powersh.ui.components.finalCartItem
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.YellowOnboarding
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.getCommuneFromWilayaJson
import akram.bensalem.powersh.utils.getDairaFromWilayaJson
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.akram.bensalem.powersh.ui.screens.login.autofill
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import java.text.DecimalFormat
import java.text.NumberFormat

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun checkoutScreen(
    navController: NavController,
    cartProduct: MutableList<CardItem>,
) {

    val step = remember {
        mutableStateOf(0)
    }
    var selected = remember { mutableStateOf(MainPayOptions.CASH_OPTION) }
    val openDialog = remember { mutableStateOf(false)  }


    val totalPrice = remember {
        mutableStateOf(0)
    }


    totalPrice.value = 0

    for (item in cartProduct) {
        totalPrice.value += item.price * item.quantity
    }



    Scaffold(
        backgroundColor = Color.White,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp)
            ) {

                AnimatedVisibility(
                    visible = step.value > 0,
                    enter = fadeIn() + slideInHorizontally(),
                    exit = fadeOut() + slideOutHorizontally(
                        targetOffsetX = {-it},
                        spring()
                    ),
                ) {
                    OutlinedButton(
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.primary),
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        onClick = {
                            if (step.value > 0)
                                step.value -= 1
                        }) {
                        Text(
                            text = "Back",
                            color = MaterialTheme.colors.primary,
                            style = TextStyle(
                                background = Color.White,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 2.dp,
                                bottom = 2.dp
                            )
                        )
                    }


                }

                Spacer(
                    modifier =
                    Modifier
                        .weight(1f)
                )



                Button(
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White,
                        disabledBackgroundColor = CardCoverPink,
                        disabledContentColor = CardCoverPink,
                    ),
                    modifier = Modifier
                        .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                        .align(Alignment.CenterVertically),
                    onClick = {
                        if (step.value < 2) {
                            step.value += 1
                        }else if(step.value == 2){
                            openDialog.value = true
                        }

                    }) {
                    Text(
                        text = if (step.value < 2) "Next" else "Confirm",
                        color = YellowOnboarding,
                        style = TextStyle(
                            background = MaterialTheme.colors.primary,
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 2.dp,
                            bottom = 2.dp
                        )
                    )
                }


            }


        },
        topBar = {

            Column(Modifier.background(Color.White)) {
                TopAppBar(
                    backgroundColor = Color.White,
                    title = {
                        Text(
                            color = Color.Black,
                            text = "Checkout"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()

                        }) {
                            Icon(
                                tint = Color.Black,
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Back to Cart Screen"
                            )
                        }
                    },
                    actions = {},
                    elevation = 0.dp,
                )
                stepsWizard(step, totalPrice)
            }

        },
        modifier = Modifier.statusBarsPadding().navigationBarsPadding()
    ) {


        firstStep(
            step.value == 0,
            selected = selected,
            totalPrice = totalPrice
        )
        secondStep(
            visible = step.value == 1,
            selected = selected
        )
        thirdStep(
            step.value == 2,
            openDialog = openDialog,
            cartProduct = cartProduct,
            totalPrice = totalPrice
        )


    }


}


@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun thirdStep(
    visible: Boolean,
    openDialog: MutableState<Boolean>,
    cartProduct: MutableList<CardItem>,
    totalPrice: MutableState<Int>,) {

    AnimatedVisibility(
        visible = visible,
        enter =fadeIn() + slideInHorizontally(),
        exit =fadeOut() + slideOutHorizontally(
            targetOffsetX ={ it },
            animationSpec=  spring()
        ),
        modifier = Modifier
            .fillMaxSize()
    )
    {

        Box(modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 64.dp, start = 16.dp, end = 16.dp)
            .background(
                Color.Transparent,
                RoundedCornerShape(12.dp),
            )
        ) {


            ConfirmAlertDialog(openDialog = openDialog)


            Card(
                backgroundColor = Color.White,
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp),
                modifier =
                Modifier
                    .fillMaxSize()
                    .align(Center)
            ) {

                LazyColumn (
                    modifier = Modifier.padding(start = 0.dp, end = 0.dp),
                    contentPadding = PaddingValues(
                        top = 2.dp,
                        bottom = 16.dp
                    )
                ){

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 16.dp, end = 16.dp)
                        ) {


                            Text(
                                text = "Your Billing",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, bottom = 16.dp)
                            )

                            factureText(
                                title = "ID:",
                                detail = "22154854"
                            )
                            factureText(
                                title = "To:",
                                detail = "Bensalem Akram"
                            )
                            factureText(
                                title = "Phone Number:",
                                detail = "0772375348"
                            )
                            factureText(
                                title = "Shipping Address:",
                                detail = "11 Ain Bensultan, Ain D'Hab, Médéa, Médéa"
                            )
                            factureText(
                                title = "Payment Method:",
                                detail = "CCP"
                            )
                            factureText(
                                title = "Date:",
                                detail = "20/06/2021"
                            )


                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            )

                            /* Image(
                                 painter = painterResource(id = R.drawable.powersh_icon),
                                 contentDescription = "PowerSh",
                                 modifier = Modifier
                                     .align(CenterHorizontally)T
                                     .clip(CircleShape)

                             )
         */


                        }
                    }
                    item {
                        Text(
                            text = "Your Purchases:",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .padding(start = 16.dp ,top = 4.dp, bottom = 8.dp)
                        )

                    }
                    itemsIndexed(cartProduct) { index, row ->
                        finalCartItem(product = row){
                            // do something
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                    }

                }



            }



            // here
            Row(
                modifier  = Modifier
                    .fillMaxWidth()
                    .align(BottomEnd)
                    .padding(top = 8.dp, bottom = 0.dp, end = 12.dp)
                    .background(
                        Color.White, RoundedCornerShape(
                            bottomEnd = 12.dp,
                            bottomStart = 12.dp
                        )
                    )
            ) {

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Text(
                    text = "Total Amount:",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(top = 4.dp, bottom = 4.dp)
                )

                Text(
                    text = "${totalPrice.value} DA",
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }


    }
}




@Composable
fun factureText(
    title : String,
    detail : String
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 4.dp, bottom = 2.dp)
        )

        Text(
            text = detail,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(CenterVertically)
                .padding(start = 4.dp, top = 4.dp, bottom = 2.dp)
        )
    }

}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun firstStep(visible: Boolean, selected: MutableState<String>, totalPrice: MutableState<Int>) {


    AnimatedVisibility(
        visible = visible,
        enter =fadeIn() + slideInHorizontally(),
        exit =fadeOut() + slideOutHorizontally(
            targetOffsetX ={ it },
            animationSpec=  spring()
        ),
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            displayRadioGroup(selected = selected)
            cashOption(visible = selected.value == MainPayOptions.CASH_OPTION , price = totalPrice.value, Modifier.weight(1f))
            ccpOption(visible = selected.value == MainPayOptions.CCP_OPTION , price = totalPrice.value, Modifier.weight(1f))
        }
    }
}


@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun secondStep(visible: Boolean, selected: MutableState<String>) {

    val wilayaList = Constants.wilayaList

    val wilayaSelectedIndex = remember { mutableStateOf(0) }
    val dairaSelectedIndex = remember { mutableStateOf(0) }
    val communeSelectedIndex = remember { mutableStateOf(0) }


    val fullAddressState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val firstNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val lastNameState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val phoneState = remember {
        mutableStateOf(TextFieldValue(""))
    }

    val mFullAddressRequester = remember { FocusRequester() }

    val mphoneRequester = remember { FocusRequester() }
    val mfirstNameRequester = remember { FocusRequester() }

    val mlastNameRequester = remember { FocusRequester() }

    val view = LocalView.current

    val context = LocalContext.current

    var   dairaList  = getDairaFromWilayaJson(
        context,
        wilayaSelectedIndex.value,
    )
    var   communeList  = getCommuneFromWilayaJson(context, wilayaSelectedIndex.value , dairaList[dairaSelectedIndex.value])



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
                    .align(Center)
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

                    wilayaDropDownMenu(wilayaList, wilayaSelectedIndex, dairaSelectedIndex ,communeSelectedIndex )
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



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun customTextField(
    modifier: Modifier,
    textFieldModifier: Modifier = Modifier,
    title:  String,
    fieldState: MutableState<TextFieldValue>,
    focusRequester: FocusRequester,
    autofillType: AutofillType,
    keyboardType: KeyboardType,
    imeAction : ImeAction = ImeAction.Next,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {}
){






    var isFieldFocus by remember {
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
            singleLine = true,
            keyboardActions = KeyboardActions(
                onNext = {
                    onNext()
                },
                onDone = {
                    onDone()
                }
            ),
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


@Composable
fun wilayaDropDownMenu(items: SnapshotStateList<String>, wilayaSelectedIndex: MutableState<Int>,  dairaSelectedIndex : MutableState<Int>,  communeSelectedIndex : MutableState<Int>) {

    var expanded by remember { mutableStateOf(false) }
    val f: NumberFormat = DecimalFormat("00")


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 12.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))

    ) {
        Text(
            text =if (wilayaSelectedIndex.value != 0 )  "${f.format(wilayaSelectedIndex.value)}- ${items[wilayaSelectedIndex.value]}" else "Wilaya",
            color =if (wilayaSelectedIndex.value != 0) Color.Black else Color.Gray ,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
                .background(Color.White)
        )
        IconButton(onClick = {  },
            modifier = Modifier.align(CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =
            Modifier
                .background(Color.White)
        ) {
            items.forEachIndexed {
                    index, s ->
                DropdownMenuItem(onClick = {
                    communeSelectedIndex.value = 0
                    dairaSelectedIndex.value = 0
                    expanded = false
                    wilayaSelectedIndex.value = index
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text =if (index != 0 ) "${f.format(index)}- ${s}" else s,
                            style = MaterialTheme.typography.subtitle2,
                            color = if (index != 0) Color.Black else Color.Gray
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun diaraDropDownMenu(
    items: SnapshotStateList<String>,
    selectedIndex: MutableState<Int>,
    communeSelectedIndex: MutableState<Int>
) {

    var expanded by remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 12.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))

    ) {



        Text(
            text =if (selectedIndex.value != 0 ) items[selectedIndex.value] else "Daira",
            color =if (selectedIndex.value != 0) Color.Black else Color.Gray ,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
                .background(Color.White)
        )
        IconButton(onClick = {  },
            modifier = Modifier.align(CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =
            Modifier
                .background(Color.White)
        ) {
            items.forEachIndexed {
                    index, s ->
                DropdownMenuItem(onClick = {
                    communeSelectedIndex.value = 0
                    expanded = false
                    selectedIndex.value = index
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index != 0 )  s else "Daira",
                            style = MaterialTheme.typography.subtitle2,
                            color = if (index != 0) Color.Black else Color.Gray
                        )
                    }

                }
            }
        }
    }
}




@Composable
fun communeDropDownMenu(items: SnapshotStateList<String>, selectedIndex: MutableState<Int>) {

    var expanded by remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp, 12.dp)
        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))

    ) {
        Text(
            text =if (selectedIndex.value != 0 ) items[selectedIndex.value] else "Commune",
            color =if (selectedIndex.value != 0) Color.Black else Color.Gray ,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
                .background(Color.White)
        )
        IconButton(onClick = {  },
            modifier = Modifier.align(CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier =
            Modifier
                .background(Color.White)
        ) {
            items.forEachIndexed {
                    index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index!= 0 )  s else "Commune",
                            style = MaterialTheme.typography.subtitle2,
                            color = if (index != 0) Color.Black else Color.Gray
                        )
                    }

                }
            }
        }
    }
}





@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ccpOption(visible: Boolean, price: Int, modifier: Modifier) {

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
        modifier
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
                    .align(Center)
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                ) {


                    Image(
                        painter = painterResource(id = R.drawable.ic_credit_card),
                        contentDescription = "Cash on delivery",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(120.dp)
                            .align(CenterHorizontally)
                    )

                    Text(
                        text = "Pay via CCP or BaridiMob",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Text(
                        text = "Please send the total amount that you will pay to this CCP account:",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "Our CCP account: xxxxxxxxxx xx",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )
                    Text(
                        text = "The total amount: ${price} DA",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 0.dp, bottom = 8.dp)
                    )

                    Text(
                        text = "Then send a proof by uploading an image of the transcription here",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Button(
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            disabledBackgroundColor = CardCoverPink,
                            disabledContentColor = CardCoverPink,
                        ),
                        modifier = Modifier
                            .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                            .align(CenterHorizontally),
                        onClick = {

                        }) {
                        Text(
                            text ="UPLOAD",
                            fontSize = 11.sp,
                            color = YellowOnboarding,
                            style = TextStyle(
                                background = MaterialTheme.colors.primary,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 0.dp,
                                bottom = 0.dp
                            )
                        )
                    }


                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                    Text(
                        text = "Dear customer, make sure to keep the proof of payment as you will receive a call from our customer service to confirm your order",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 8.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                }
            }


            Column(
                Modifier
                    .fillMaxSize()
                    .align(Center)
                    .background(
                        color = CardCoverPink.copy(alpha = 0.1f)
                    )
            ) {}


        }

    }
}










@OptIn(ExperimentalAnimationApi::class)
@Composable
fun cashOption(visible: Boolean, price: Int, modifier: Modifier) {


    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut(),
        modifier = Modifier.fillMaxSize()
    )
    {

        Box(modifier =
        modifier
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
                    .align(Center)
            ) {

                Column(
                    Modifier
                        .fillMaxSize()
                ) {


                    Image(
                        painter = painterResource(id =R.drawable.ic_wallet),
                        contentDescription = "Cash on delivery",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .align(CenterHorizontally)
                    )

                    Text(
                        text = "Cash on delivery",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))
                    Text(
                        text = "In the Cash on delivery payment system, you have to fill the form and we will send you your purchases within 2 days",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                    Text(
                        text = "The total amount you will pay is : ${price}DA",
                        color = Color.Black,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f))

                    Button(
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = Color.White,
                            disabledBackgroundColor = CardCoverPink,
                            disabledContentColor = CardCoverPink,
                        ),
                        modifier = Modifier
                            .background(color = CardCoverPink, shape = RoundedCornerShape(14.dp))
                            .align(CenterHorizontally),
                        onClick = {

                        }) {
                        Text(
                            text ="CALL US",
                            fontSize = 11.sp,
                            color = YellowOnboarding,
                            style = TextStyle(
                                background = MaterialTheme.colors.primary,
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 0.dp,
                                bottom = 0.dp
                            )
                        )
                    }


                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f))

                    Text(
                        text = "Dear customer, make sure to fill your information carefully and you will be receive a call from our customer service to confirm your order",
                        color = Color.Black,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                }
            }


            Column(
                Modifier
                    .fillMaxSize()
                    .align(Center)
                    .background(
                        color = CardCoverPink.copy(alpha = 0.1f)
                    )
            ) {}


        }






    }
}

@Composable
fun radioPaymentOption(
    selected: MutableState<String>,
    id: String,
    title: String,
    icon: ImageVector,
    contentDescription: String = "",
    detail: String,
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 8.dp)
            .clickable(onClick = { selected.value = id })
    ) {
        RadioButton(
            selected = selected.value == id,
            onClick = { selected.value = id },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier
                .padding(start = 0.dp, end = 8.dp)

        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { selected.value = id })
        ) {
            Text(
                text = title,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = { selected.value = id })
                    .padding(start = 4.dp)
            )
            Text(
                text = detail,
                color = Color.DarkGray,
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable(onClick = { selected.value = id })
                    .padding(start = 4.dp)
            )
        }

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (selected.value == id) MaterialTheme.colors.primary else Color.DarkGray,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

    }
}


@Composable
fun displayRadioGroup(selected: MutableState<String>) {


       Column(
           Modifier
               .fillMaxWidth()
               .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
               .background(
                   color = YellowOnboarding.copy(alpha = 0.2f)
               )
       ) {
       Card(
           elevation = 2.dp,
           backgroundColor = Color.White,
           shape =  RoundedCornerShape(12.dp),
           modifier =  Modifier
               .align(CenterHorizontally)

       ) {

           Column(
               modifier = Modifier.fillMaxWidth()
           ) {
               radioPaymentOption(
                   selected = selected,
                   id = MainPayOptions.CASH_OPTION,
                   title = "Cash on Delivery",
                   detail = "Pay when the products are delivered to you",
                   contentDescription = "Cash on Delivery icon",
                   icon = Icons.Outlined.Money,
               )

               radioPaymentOption(
                   selected = selected,
                   id = MainPayOptions.CCP_OPTION,
                   title = "CCP or BaridiMob",
                   detail = "Pay now using your CCP or BaridiMob",
                   contentDescription = "CCP or BaridiMob icon",
                   icon = Icons.Outlined.CreditCard,
               )
           }


       }

   }



}


object MainPayOptions {


    const val CASH_OPTION = "chash"
    const val CCP_OPTION = "ccp"


}


@Composable
fun StepItem(
    s: Step,
    index: Int,
    lastIndex: Int,
    selected: Int,
    modifier: Modifier
) {

    val color = if (index <= selected) MaterialTheme.colors.primary else Color.Gray


    val backgroundColor by animateColorAsState(
        color,
        spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow)
    )


    ConstraintLayout(
        modifier = modifier.padding(top = 8.dp, end = 8.dp)
    ) {
        val (circle, text, leftLine, rightLine) = createRefs()
        Spacer(
            modifier =
            Modifier
                .height(1.dp)
                .fillMaxWidth(0.5f)
                .constrainAs(leftLine) {
                    start.linkTo(parent.start)
                    end.linkTo(circle.start)
                    top.linkTo(circle.top)
                    bottom.linkTo(circle.bottom)
                }
                .background(if (index == 0) Color.Transparent else backgroundColor)
        )

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth(0.5f)
                .constrainAs(rightLine) {
                    start.linkTo(circle.end)
                    top.linkTo(circle.top)
                    bottom.linkTo(circle.bottom)
                }
                .background(
                    if (index == lastIndex) Color.Transparent else {
                        if (selected > index) MaterialTheme.colors.primary else Color.Gray
                    }
                )
        )


        Box(
            Modifier
                .padding(0.dp)
                .background(color = Color.White)
                .border(1.dp, backgroundColor, CircleShape)
                .constrainAs(circle) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }

        )
        {
            Text(
                text = "",
                modifier =
                Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .width(16.dp)
                    .height(16.dp)
                    .background(color = backgroundColor, shape = CircleShape)
            )
        }


        Text(
            text = s.title,
            color = backgroundColor,
            fontSize = 14.sp,
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(circle.start)
                    end.linkTo(circle.end)
                    top.linkTo(circle.bottom)
                }

        )


    }


}


@Composable
fun stepsWizard(
    step: MutableState<Int>,
    totalPrice: MutableState<Int>
) {

    val steps = remember { Constants.stepList }

    // steps : MutableList<Step>

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, bottom = 16.dp, top = 8.dp)
    ) {

        steps.forEachIndexed { index, s ->
            StepItem(
                s,
                index,
                steps.size - 1,
                step.value,
                Modifier.weight(1f),
            )
        }
    }

}


@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun checkoutPreview() {

    val navController = rememberNavController()
    val cartProduct = remember { Constants.cartList2 }
    PowerSHTheme {
        checkoutScreen(
            navController = navController,
            cartProduct = cartProduct
        )
    }
}