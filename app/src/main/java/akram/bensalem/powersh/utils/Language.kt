package akram.bensalem.powersh.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Laptop
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.ui.graphics.vector.ImageVector

enum class Language(
    val languageName: String,
    val icon: ImageVector,
    val languageValue: Int
) {

    FOLLOW_SYSTEM(
        languageName = "Follow System Settings",
        icon = Icons.Outlined.SettingsSuggest,
        languageValue = -1
    ),
    ARABIC(
        languageName = "العربية",
        icon = Icons.Outlined.Laptop,
        languageValue = 1
    ),
    FRENCH(
        languageName = "Français",
        icon = Icons.Outlined.Laptop,
        languageValue = 2
    ),
    ENGLISH(
        languageName = "English",
        icon = Icons.Outlined.Laptop,
        languageValue = 3
    ),
}