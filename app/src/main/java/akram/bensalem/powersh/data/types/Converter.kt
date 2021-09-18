package akram.bensalem.powersh.data.types

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun fromShoeType(type: ShoeType): Int {
        return when (type) {
            ShoeType.MEN -> 1
            ShoeType.WOMEN -> 2
            ShoeType.BABY -> 3
            else -> 0
        }
    }

    @TypeConverter
    fun toShoeType(shoeType: Int): ShoeType {
        return when (shoeType) {
            1 -> ShoeType.MEN
            2 -> ShoeType.WOMEN
            3 -> ShoeType.BABY
            else -> ShoeType.UNKOWN
        }
    }

}