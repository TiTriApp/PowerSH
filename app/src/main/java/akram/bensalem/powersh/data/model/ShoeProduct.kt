package akram.bensalem.powersh.data.model

import akram.bensalem.powersh.data.types.ShoeType
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoeProduct(
    val id:Int,
    val title: String,
    val imageUrl: String,
    val type: ShoeType,
    val releaseDate: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int
) : Parcelable
