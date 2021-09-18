package akram.bensalem.powersh.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.IceSkating
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.PriceChange
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.ui.graphics.vector.ImageVector

enum class Sort(
    val type: String,
    val icon: ImageVector,
    val sortValue: Int
) {
    ALPHABETICAL_ASC(
        type = "Alphabetical Order",
        icon = Icons.Outlined.SortByAlpha,
        sortValue = 0
    ),
    NEW_RELEASE_FIRST(
        type = "Newest Release First",
        icon = Icons.Outlined.IceSkating,
        sortValue = 1
    ),
    OLD_RELEASE_FIRST(
        type = "Oldest Release First",
        icon = Icons.Outlined.Inventory2,
        sortValue = 2
    ),
    LOW_PRICE_FIRST(
        type = "Lowest Price First",
        icon = Icons.Outlined.PriceChange,
        sortValue = 3
    ),
    HIGH_PRICE_FIRST(
        type = "Highest Price First",
        icon = Icons.Outlined.PriceChange,
        sortValue = 4
    )
}