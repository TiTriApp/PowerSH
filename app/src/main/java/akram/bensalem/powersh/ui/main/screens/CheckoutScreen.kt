package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.Step
import akram.bensalem.powersh.ui.components.ConfirmAlertDialog
import akram.bensalem.powersh.ui.components.checkout.firstStep
import akram.bensalem.powersh.ui.components.checkout.secondStep
import akram.bensalem.powersh.ui.components.checkout.thirdStep
import akram.bensalem.powersh.ui.components.finalCartItem
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.ui.theme.YellowOnboarding
import akram.bensalem.powersh.utils.*
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Money
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.text.DecimalFormat
import java.text.NumberFormat


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun checkoutScreen(
    navController: NavController,
    cartProduct: MutableList<CardItem>,
    onConfirmClicked : (OrderItem) -> Unit = {},
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





    val fullAddress = remember {
        mutableStateOf("")
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

    val date = remember {
        mutableStateOf(getCurrentDate())
    }

    val imageUri  = remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current


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

                            if(step.value == 0 ){

                                if (selected.value == MainPayOptions.CCP_OPTION) {
                                    if (imageUri.value == null){
                                        Toast.makeText(context, "You forget to upload an image of your payment" , Toast.LENGTH_SHORT).show()
                                    }else {
                                        step.value += 1
                                    }

                                }else {
                                    step.value += 1
                                }

                            }else if(step.value == 1) {
                               if (fullAddress.value.isNotEmpty() && phoneState.value.text.isNotEmpty() && firstNameState.value.text.isNotEmpty() && lastNameState.value.text.isNotEmpty()) {
                                   step.value += 1
                               } else {
                                   Toast.makeText(context, "One of filed or many are empty" , Toast.LENGTH_SHORT).show()
                               }

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
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {

        firstStep(
            step.value == 0,
            selected = selected,
            totalPrice = totalPrice,
            imageUri = imageUri,
            onCallUsClicked = {
                callPhone(
                    context = context
                )
            }
        )

        secondStep(
            visible = step.value == 1,
            fullAddress = fullAddress,
            phoneState = phoneState,
            lastNameState =lastNameState,
            firstNameState = firstNameState,
        )

        thirdStep(
            step.value == 2,
            selected = selected,
            openDialog = openDialog,
            cartProduct = cartProduct,
            totalPrice = totalPrice,
            fullAddressState = fullAddress,
            phoneState = phoneState,
            lastNameState =lastNameState,
            firstNameState = firstNameState,
            date = date.value,
            onConfirmClicked = onConfirmClicked
        )


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
fun wilayaDropDownMenu(
    items: SnapshotStateList<String>,
    wilayaSelectedIndex: MutableState<Int>,
    dairaSelectedIndex: MutableState<Int>,
    communeSelectedIndex: MutableState<Int>
) {

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


@ExperimentalPermissionsApi
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