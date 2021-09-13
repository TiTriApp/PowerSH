package akram.bensalem.powersh.data.model

import akram.bensalem.powersh.ui.main.screens.MainPayOptions
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.getCurrentDate
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.collections.ArrayList


enum class OrderStatus{
    PENDING, DELIVERED, ACCEPTED, REJECTED
}

data class OrderItem(
    val id:Int = 1,
    val date:String = getCurrentDate(),
    val total : Int = 0,
    val status : OrderStatus = OrderStatus.PENDING,
    val Address : String ="Ain bensultan, Médéa, Médéa",
    val payment : String = MainPayOptions.CASH_OPTION,
    val productList: List<CardItem>,
)