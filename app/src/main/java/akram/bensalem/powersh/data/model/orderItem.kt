package akram.bensalem.powersh.data.model

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.components.checkout.MainPayOptions
import akram.bensalem.powersh.utils.getCurrentDate


enum class OrderStatus {
    PENDING, DELIVERED, ACCEPTED, REJECTED
}

data class OrderItem(
    val id: Int = 1,
    val date: String,
    val total: Int = 0,
    val status: OrderStatus = OrderStatus.PENDING,
    val Address: String = "Ain bensultan, Médéa, Médéa",
    val payment: String = MainPayOptions.CASH_OPTION,
    val productList: List<CardItem>,
)