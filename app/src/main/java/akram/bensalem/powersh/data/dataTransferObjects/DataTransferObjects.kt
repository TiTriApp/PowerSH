package akram.bensalem.powersh.data.dataTransferObjects

import akram.bensalem.powersh.data.database.PowerSHDatabaseObject
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.data.responses.PowerSHShoesResponse

fun List<PowerSHShoesResponse>.asDatabaseModel(): Array<PowerSHDatabaseObject> {
    return map {
        PowerSHDatabaseObject(
            id= it.id,
            model = it.title,
            imageUrl = it.imageUrl,
            releaseDate = it.releaseDate,
            marketPriceStart = it.marketPriceStart,
            marketPriceEnd = it.marketPriceEnd,
        )
    }.toTypedArray()
}

fun List<PowerSHDatabaseObject>.asDomainModel(): List<ShoeProduct> {
    return map {
        ShoeProduct(
            id = it.id,
            title = it.model,
            imageUrl = it.imageUrl,
            releaseDate = it.releaseDate,
            marketPriceStart = it.marketPriceStart,
            marketPriceEnd = it.marketPriceEnd,
        )
    }
}

fun PowerSHDatabaseObject.asThinkpad(): ShoeProduct {
    return ShoeProduct(
        id = this.id,
        title = this.model,
        imageUrl = this.imageUrl,
        releaseDate = this.releaseDate,
        marketPriceStart = this.marketPriceStart,
        marketPriceEnd = this.marketPriceEnd,
    )
}
