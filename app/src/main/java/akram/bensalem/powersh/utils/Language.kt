package akram.bensalem.powersh.utils

import akram.bensalem.powersh.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Laptop
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

enum class Language(
    val languageName: String,
    val icon: Int,
    val languageValue: Int
) {

    FOLLOW_SYSTEM(
        languageName = "Follow System Settings",
        icon = R.drawable.system,
        languageValue = -1
    ),
    ARABIC(
        languageName = "العربية",
        icon =  R.drawable.arabic,
        languageValue = 1
    ),
    FRENCH(
        languageName = "Français",
        icon =  R.drawable.french,
        languageValue = 2
    ),
    ENGLISH(
        languageName = "English",
        icon =  R.drawable.english,
        languageValue = 3
    ),
}