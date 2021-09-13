package akram.bensalem.powersh.utils

import akram.bensalem.powersh.data.model.Adress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import akram.bensalem.powersh.data.model.ShoeProduct
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import android.net.NetworkCapabilities

import android.net.ConnectivityManager




suspend fun List<ShoeProduct>.getChipNamesList(): List<String> {
    return withContext(Dispatchers.Default) {
        val collection = mutableSetOf<String>()
        this@getChipNamesList.forEach { thinkpad ->
            collection.add("T Series")
        }
        collection.toList()
    }
}


fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}



fun getWilayaFromJson(context: Context): ArrayList<String> {
    val jsonArray: ArrayList<String> = ArrayList()

    try {
        getJsonDataFromAsset(context, "address.json")
            ?.forEachIndexed { idx, address ->
                jsonArray.add(address.wilaya_name_ascii)
            }

    } catch (ioException: IOException) {
        ioException.printStackTrace()
    } catch (e : Exception){
        e.printStackTrace()
    }



    return jsonArray
}




fun getDairaFromWilayaJson(context: Context, wilayaId: Int ): SnapshotStateList<String> {
    val jsonArray= mutableStateListOf<String>()
    jsonArray.add("Commune")
    try {
        getJsonDataFromAsset(context, "address.json")
            ?.forEachIndexed { idx, address ->
                if (wilayaId == address.wilaya_code.toInt() && !jsonArray.contains(address.daira_name_ascii)) {
                    jsonArray.add(address.daira_name_ascii)
                }
            }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    } catch (e : Exception){
        e.printStackTrace()
    }
    return jsonArray
}







fun getCommuneFromWilayaJson(context: Context, wilayaId: Int, dairaName: String): SnapshotStateList<String> {
    val jsonArray= mutableStateListOf<String>()
    jsonArray.add("Commune")
    try {
        getJsonDataFromAsset(context, "address.json")
            ?.forEachIndexed { idx, address ->
                if (wilayaId == address.wilaya_code.toInt() && dairaName == address.daira_name_ascii) {
                    jsonArray.add(address.commune_name_ascii)
                }
            }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
    } catch (e : Exception){
        e.printStackTrace()
    }
    return jsonArray
}






fun getJsonDataFromAsset(context: Context, fileName: String): List<Adress>? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        val gson = Gson()
        val listAddressType = object : TypeToken<List<Adress>>() {}.type

        return gson.fromJson(jsonString, listAddressType)

    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    } catch (e : Exception){
        return null
    }
}



