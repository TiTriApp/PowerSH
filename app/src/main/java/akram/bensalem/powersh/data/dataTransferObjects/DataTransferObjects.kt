package akram.bensalem.powersh.data.dataTransferObjects

import akram.bensalem.powersh.data.database.PowerSHDatabaseObject
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.data.responses.PowerSHShoesResponse
import akram.bensalem.powersh.data.types.ShoeType

fun List<PowerSHShoesResponse>.asDatabaseModel(): Array<PowerSHDatabaseObject> {
    return map {
        PowerSHDatabaseObject(
            id= it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            type=  toShoeType(it.type),
            releaseDate = it.releaseDate,
            marketPriceStart = it.marketPriceStart,
            marketPriceEnd = it.marketPriceEnd,
        )
    }.toTypedArray()
}



private fun toShoeType(shoeType: Int): ShoeType {
    return when (shoeType){
        1 -> ShoeType.MEN
        2 -> ShoeType.WOMEN
        3 -> ShoeType.BABY
        else -> ShoeType.UNKOWN
    }
}

fun List<PowerSHDatabaseObject>.asDomainModel(): List<ShoeProduct> {
    return map {
        ShoeProduct(
            id = it.id,
            title = it.title,
            imageUrl = it.imageUrl,
            type = it.type,
            releaseDate = it.releaseDate,
            marketPriceStart = it.marketPriceStart,
            marketPriceEnd = it.marketPriceEnd,
        )
    }
}

fun PowerSHDatabaseObject.asThinkpad(): ShoeProduct {
    return ShoeProduct(
        id = this.id,
        title = this.title,
        imageUrl = this.imageUrl,
        type = this.type,
        releaseDate = this.releaseDate,
        marketPriceStart = this.marketPriceStart,
        marketPriceEnd = this.marketPriceEnd,
    )
}
