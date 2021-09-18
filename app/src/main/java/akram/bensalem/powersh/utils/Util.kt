package akram.bensalem.powersh.utils

import akram.bensalem.powersh.data.model.Adress
import akram.bensalem.powersh.data.model.ShoeProduct
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern









fun isPhoneValid(phone : String): Boolean{
    return if (phone.isEmpty()) {
        true
    } else {
        android.util.Patterns.PHONE.matcher(phone).matches()
    }
}


fun isNameValid(name : String): Boolean{
    return if (name.isEmpty()) {
        true
    } else {
       name.length >= 3
    }
}



fun isRepeatPasswordValid(password : String, repeatPassword : String): Boolean{
    return if (repeatPassword.isEmpty()) {
        true
    } else {
        password == repeatPassword
    }
}



fun isEmailValid(email : String): Boolean{
    return if (email.isEmpty()) {
        true
    } else {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}


fun isValidPasswordFormat(password: String): Boolean {

    return if (password.isEmpty()){
        true
    } else {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[<>{}|;:.,~!?@#%^=&*()¿§«»ω⊙¤°℃℉€¥£¢¡®©_+])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        passwordREGEX.matcher(password).matches()
    }


}

fun ErrorMessageOfPassword(password: String): String {

    val oneDigit = "(.*[0-9].*)" //at least 1 digit
    val oneLowerCase = "(.*[a-z].*)" //at least 1 lower case letter
    val oneUpperCase = "(.*[A-Z].*)" //at least 1 upper case letter
    val anyLetter = "(.*[a-zA-Z].*)" // any letter
    val oneSpecialCharacter = "(.*[<>{}|;:.,~!?@#%^=&*()¿§«»ω⊙¤°℃℉€¥£¢¡®©_+].*)" //at least one special character
    val noWhiteSpace = "(.\\S+$)"
    val more8Character = ".{8,}"

    return if (!isRegrexMatch(regrex = more8Character, password = password)){
        "Password too short!"
    } else if (!isRegrexMatch(regrex = noWhiteSpace, password = password)){
        "White Space is not allowed!"
    }else if (!isRegrexMatch(regrex = oneDigit, password = password)){
        "At least One Digit!"
    }else if (!isRegrexMatch(regrex = oneLowerCase, password = password)){
        "At least One Lower Case letter!"
    }else if (!isRegrexMatch(regrex = oneUpperCase, password = password)){
        "At least One Upper Case letter!"
    }else if (!isRegrexMatch(regrex = anyLetter, password = password)){
        "At least One letter!"
    }else if (!isRegrexMatch(regrex = oneSpecialCharacter, password = password)){
        "At least Special Character!"
    } else ""
}

fun isRegrexMatch(regrex : String, password: String): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" +
                regrex +
                "$")
    return passwordREGEX.matcher(password).matches()
}




@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    onFill: ((String) -> Unit),
) = composed {
    val autofill = LocalAutofill.current
    val autofillNode = AutofillNode(onFill = onFill, autofillTypes = autofillTypes)
    LocalAutofillTree.current += autofillNode

    this
        .onGloballyPositioned {
            autofillNode.boundingBox = it.boundsInWindow()
        }
        .onFocusChanged { focusState ->
            autofill?.run {
                if (focusState.isFocused) {
                    requestAutofillForNode(autofillNode)
                } else {
                    cancelAutofillForNode(autofillNode)
                }
            }
        }
}



fun callPhone(context: Context, phoneNumber: String = "+213555753567") {
    val dialIntent = Intent(Intent.ACTION_DIAL)
    dialIntent.data = Uri.parse("tel:$phoneNumber")
    context.startActivity(dialIntent)
}


fun getCurrentDate(): String {

    val sdf = SimpleDateFormat(
        "dd/MM/yyyy hh:mm:ss",
        Locale.getDefault()
    )

    return sdf.format(Date())
}

suspend fun List<ShoeProduct>.getChipNamesList(): List<String> {
    return withContext(Dispatchers.Default) {
        val collection = mutableSetOf<String>()
        this@getChipNamesList.forEach { _ ->
            collection.add("T Series")
        }
        collection.toList()
    }
}


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                Timber.i("NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                Timber.i("NetworkCapabilities.TRANSPORT_WIFI")
                return true
            }
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                Timber.i("NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}


fun getDairaFromWilayaJson(context: Context, wilayaId: Int): SnapshotStateList<String> {
    val jsonArray = mutableStateListOf<String>()
    jsonArray.add("Commune")
    try {
        getJsonDataFromAsset(context, "address.json")
            ?.forEachIndexed { _, address ->
                if (wilayaId == address.wilaya_code.toInt() && !jsonArray.contains(address.daira_name_ascii)) {
                    jsonArray.add(address.daira_name_ascii)
                }
            }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return jsonArray
}


fun getCommuneFromWilayaJson(
    context: Context,
    wilayaId: Int,
    dairaName: String
): SnapshotStateList<String> {
    val jsonArray = mutableStateListOf<String>()
    jsonArray.add("Commune")
    try {
        getJsonDataFromAsset(context, "address.json")
            ?.forEachIndexed { _, address ->
                if (wilayaId == address.wilaya_code.toInt() && dairaName == address.daira_name_ascii) {
                    jsonArray.add(address.commune_name_ascii)
                }
            }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return jsonArray
}


fun getJsonDataFromAsset(context: Context, fileName: String): List<Adress>? {
    val jsonString: String
    return try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val gson = Gson()
        val listAddressType = object : TypeToken<List<Adress>>() {}.type

        gson.fromJson(jsonString, listAddressType)

    } catch (ioException: IOException) {
        ioException.printStackTrace()
        null
    } catch (e: Exception) {
        null
    }
}



