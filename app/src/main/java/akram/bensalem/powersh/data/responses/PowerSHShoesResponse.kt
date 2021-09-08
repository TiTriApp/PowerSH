package akram.bensalem.powersh.data.responses

import com.squareup.moshi.Json

data class PowerSHShoesResponse(
    @Json(name = "id")
    val id:Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "image_url")
    val imageUrl: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "market_price_start")
    val marketPriceStart: Int,
    @Json(name = "market_price_end")
    val marketPriceEnd: Int,

    )
