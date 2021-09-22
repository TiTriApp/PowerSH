package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.ui.components.checkout.*
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.*
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CheckoutScreen(
    cartProduct: MutableList<CardItem>,
    onConfirmClicked: (OrderItem) -> Unit = {},
    onBackPressClicked: () -> Unit = {}
) {

    val step = remember {
        mutableStateOf(2)
    }
    val selected = remember { mutableStateOf(MainPayOptions.CASH_OPTION) }
    val openDialog = remember { mutableStateOf(false) }


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

    val emailState = remember {
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

    val localString = LocalStrings.current

    val date = remember {
        mutableStateOf(getCurrentDate(localString))
    }

    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current


    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        bottomBar = {
            BottomBarCheckoutScreen(
                step = step,
                enabled = ifNextIsEnabled(
                    step = step.value,
                    paymentOption= selected.value,
                    isImageLoaded= imageUri.value != null,
                    email= emailState.value.text,
                    firstName = firstNameState.value.text,
                    lastName= lastNameState.value.text,
                    phone= phoneState.value.text,
                    fullAddress= fullAddress.value
                ),
                onNextPressed = {
                    when (step.value) {
                        0 -> {
                            step.value += 1
                        }
                        1 -> {

                            step.value += 1

                        }
                        2 -> {
                            openDialog.value = true
                        }
                    }
                },
                onBackPressed = {
                    if (step.value > 0)
                        step.value -= 1
                }
            )

        },
        topBar = {
            TopAppBarCheckout(
                step = step,
                onBackPress = {
                    onBackPressClicked()
                }
            )
        },
        modifier = Modifier
    ) {

        FirstStep(
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

        SecondStep(
            visible = step.value == 1,
            fullAddress = fullAddress,
            emailState = emailState,
            phoneState = phoneState,
            lastNameState = lastNameState,
            firstNameState = firstNameState,
        )

        ThirdStep(
            step.value == 2,
            selected = selected,
            openDialog = openDialog,
            cartProduct = cartProduct,
            totalPrice = totalPrice,
            fullAddressState = fullAddress,
            phoneState = phoneState,
            lastNameState = lastNameState,
            firstNameState = firstNameState,
            date = date.value,
            onConfirmClicked = onConfirmClicked
        )


    }


}


fun ifNextIsEnabled(
    step : Int,
    paymentOption: String,
    isImageLoaded: Boolean,
    email: String,
    firstName : String,
    lastName: String,
    phone: String,
    fullAddress: String
): Boolean{
   return when(step){
        0 -> {
            if (paymentOption == MainPayOptions.CCP_OPTION){
                isImageLoaded
            } else true
        }
        1 -> {
            fullAddress.isNotEmpty()
            && phone.isNotEmpty()
            && firstName.isNotEmpty()
            && lastName.isNotEmpty()
            && email.isNotEmpty()
            && isEmailValid(email = email)
            && isPhoneValid(phone = phone)
            && isNameValid(fullAddress)
            && isNameValid(firstName)
            && isNameValid(lastName)
                    && fullAddress.split(",")[1].length > 3
        }
        2 -> { true }
       else -> true
    }



}



@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun CheckoutNightPreview() {

    val cartProduct = remember { Constants.cartList2 }
    PowerSHTheme {
        CheckoutScreen(
            cartProduct = cartProduct
        )
    }
}


@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun CheckoutPreview() {
    val cartProduct = remember { Constants.cartList2 }
    PowerSHTheme {
        CheckoutScreen(
            cartProduct = cartProduct
        )
    }
}


