package akram.bensalem.powersh.ui.main.screenStates

import akram.bensalem.powersh.ui.theme.PowerSHBlack
import akram.bensalem.powersh.ui.theme.PowerSHBlue
import akram.bensalem.powersh.ui.theme.PowerSHBrown
import akram.bensalem.powersh.ui.theme.PowerSHRed
import androidx.compose.ui.graphics.Color

const val RED = "Red"
const val Blue = "Blue"
const val Black1 = "Black"
const val White1 = "White"
const val BROWN = "Brown"




const val SIZE_37 = "37"
const val SIZE_38 = "38"
const val SIZE_39 = "39"
const val SIZE_40 = "40"
const val SIZE_41 = "41"
const val SIZE_42 = "42"
const val SIZE_43 = "43"
const val SIZE_44 = "44"

const val SKULL = "skull"
const val STRAIGHT_FACE = "straight_face"
const val SWEAR_FACE = "swear_face"
const val THUMBS_UP = "thumbs_up"
const val TEARS_OF_JOY = "tears_of_joy"
const val NICE = "nice"
const val SAD = "sad"



val ColorIds = listOf(
    Black1, Blue, RED, BROWN
)


val SizeIds = listOf(
    SIZE_37, SIZE_38, SIZE_39, SIZE_40,SIZE_41, SIZE_42, SIZE_43, SIZE_44
)


fun resIdFor(colorId: String?): Color? {
    return when(colorId) {
        RED -> PowerSHRed
        Black1 -> PowerSHBlack
        Blue -> PowerSHBlue
        BROWN -> PowerSHBrown
        else -> null
    }
}


fun sizeIdFor(sizeId: String?): Int? {
    return when(sizeId) {
        SIZE_37 -> 37
        SIZE_38 -> 38
        SIZE_39 -> 39
        SIZE_40 -> 40
        SIZE_41 -> 41
        SIZE_42 -> 42
        SIZE_43 -> 43
        SIZE_44 -> 44
        else -> null
    }
}



fun <T> List<T>.toGridList(cols: Int): List<List<T?>> =
    chunked(cols).map { row ->
        row.plus(List(size = cols - row.size) { null })
    }