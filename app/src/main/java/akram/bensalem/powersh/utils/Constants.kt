package akram.bensalem.powersh.utils

import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.data.types.ShoeType

object Constants {
 //  const val BASE_URL = "https://docs.google.com/spreadsheets/d/1cFrYzzAP7i3bzSLKuBMykz3ZNUbf-YPTqRSEAwINy_E/"

   const val BASE_URL = "https://docs.google.com/spreadsheets/d/1ryFLEuVNeEnzczIWqeJ9pLP3doZA7Vr1g5qdTdThj_o/"
    const val POWERSH_LIST_TABLE = "thinkpad_list"
    const val PREFERENCE_NAME = "settings_preference"
    const val THEME_OPTIONS = "Theme Options"
    const val SORT_OPTIONS = "Default Sort Option"

    val ShoesListPreview = listOf(
        ShoeProduct(
            id = 0,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.MEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 1,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.MEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 2,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.WOMEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 3,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.WOMEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 4,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.BABY,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        )
    )
}