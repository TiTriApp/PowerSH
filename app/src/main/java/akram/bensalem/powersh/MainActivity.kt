package akram.bensalem.powersh

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.ui.navigation.PowerSHApp
import akram.bensalem.powersh.utils.authentification.Authenticate
import androidx.compose.runtime.mutableStateListOf
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

     var authenticate : Authenticate =
         Authenticate(this)


    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Enable edge-to-edge experience and ProvideWindowInsets to the composables
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Timber.d("onCreate called")


        setContent {
            val cartProduct = remember {  mutableStateListOf<CardItem>() }

            val favouriteProduct = remember {  mutableStateListOf<ShoeProduct>() }

            val orderList = remember {  mutableStateListOf<OrderItem>() }


            val themeValue = remember {
                mutableStateOf(-1)
            }
            LaunchedEffect(key1 = themeValue) {
                dataStoreRepository.readThemeSetting.collect {
                    themeValue.value = it
                }
            }
            PowerSHApp(themeValue.value,
                cartProduct = cartProduct,
                favouriteProduct = favouriteProduct,
                orderList = orderList
            )
            Timber.d("setContent called")
        }



    }
}
