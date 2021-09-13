package akram.bensalem.powersh.data.model

import androidx.annotation.RawRes
import androidx.compose.ui.graphics.Color

data class OnBoardingItem(
    val titleId: String,
    val DescriptionId: String,
    @RawRes val contentImageId: Int,
    val color: Color
)