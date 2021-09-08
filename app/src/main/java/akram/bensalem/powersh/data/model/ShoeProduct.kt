package akram.bensalem.powersh.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShoeProduct(
    val id:Int,
    val title: String,
    val imageUrl: String,
    val releaseDate: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int
) : Parcelable
