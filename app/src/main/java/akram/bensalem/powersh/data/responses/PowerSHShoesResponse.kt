package akram.bensalem.powersh.data.responses

import akram.bensalem.powersh.data.types.ShoeType
import com.squareup.moshi.Json

data class PowerSHShoesResponse(
    @Json(name = "id")
    val id:Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "type")
    val type:Int,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "market_price_start")
    val marketPriceStart: Int,
    @Json(name = "market_price_end")
    val marketPriceEnd: Int,

    )
