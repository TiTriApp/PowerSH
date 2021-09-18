package akram.bensalem.powersh.utils

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.*

object BalloonUtils {
    fun getEditBalloon(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        description: String
    ): Balloon {
        return Balloon.Builder(context)
            .setText(description)
            .setArrowSize(10)
            .setWidthRatio(1.0f)
            .setHeight(BalloonSizeSpec.WRAP)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setMarginRight(12)
            .setMarginLeft(12)
            .setTextSize(14f)
            .setCornerRadius(8f)
            .setTextColorResource(android.R.color.darker_gray)
            .setIconDrawableResource(akram.bensalem.powersh.R.drawable.ic_info)
            .setBackgroundColorResource(android.R.color.background_light)
            .setOnBalloonDismissListener {}
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(lifecycleOwner)
            .build()
    }
}