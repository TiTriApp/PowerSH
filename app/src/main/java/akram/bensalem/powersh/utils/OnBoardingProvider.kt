package akram.bensalem.powersh.utils

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.OnBoardingItem
import akram.bensalem.powersh.ui.theme.OrangeOnboarding
import akram.bensalem.powersh.ui.theme.PowerSHRed
import akram.bensalem.powersh.ui.theme.YellowOnboarding

object OnBoardingProvider {
    val onBoardingDataList = listOf(
        OnBoardingItem(
            1,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_1_title,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_1_Description,
            R.raw.image,
            PowerSHRed
        ),
        OnBoardingItem(
            2,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_2_title,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_2_Description,
            R.raw.mobile,
            OrangeOnboarding
        ),
        OnBoardingItem(
            3,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_3_title,
            com.akram.bensalem.powersh.ui.R.string.onBoarding_page_3_Description,
            R.raw.product_page,
            YellowOnboarding
        )
    )
}