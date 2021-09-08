package akram.bensalem.powersh.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import timber.log.Timber
import akram.bensalem.powersh.ui.theme.PowerSHTheme

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun ThinkrchiveApp(themeValue: Int = 0) {
    ProvideWindowInsets {
        PowerSHTheme(theme = themeValue) {
            Timber.d("ThinkrchiveApp called")
            val navController = rememberAnimatedNavController()
            ThinkrchiveNavHost(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}