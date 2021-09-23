package akram.bensalem.powersh

import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.OrderItem
import akram.bensalem.powersh.data.model.ShoeProduct
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.ui.navigation.PowerSHApp
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authenticate
import akram.bensalem.powersh.utils.localization.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import java.util.*
import javax.inject.Inject



val strings = mapOf(
    Locales.EN to EnStrings,
    Locales.AR to ArStrings,
    Locales.FR to FrStrings
)


val LocalStrings = staticCompositionLocalOf { EnStrings }

lateinit var lyricist: Lyricist<Strings>


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

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


        setContent {


            lyricist = rememberStrings(strings)


            val cartProduct = remember { mutableStateListOf<CardItem>() }

            val favouriteProduct = remember { mutableStateListOf<ShoeProduct>() }

            val orderList = remember { mutableStateListOf<OrderItem>() }



            val themeValue = remember {
                mutableStateOf(-1)
            }

            val languageValue = remember {
                mutableStateOf(-1)
            }

            val layoutDirectionValue = remember {
                mutableStateOf(fromValueToLanguage(languageValue.value,Locale.getDefault().language ))
            }

            val isOnBoardingStart = remember {
                mutableStateOf(true)
            }

            LaunchedEffect(key1 = languageValue ){
                dataStoreRepository.readLanguageSetting.collect {
                    languageValue.value = it

                    when (languageValue.value) {
                        1 -> {
                            lyricist.languageTag = Locales.AR
                            layoutDirectionValue.value = LayoutDirection.Rtl
                        }
                        2 -> {
                            lyricist.languageTag = Locales.FR
                            layoutDirectionValue.value = LayoutDirection.Ltr
                        }
                        3 -> {
                            lyricist.languageTag = Locales.EN
                            layoutDirectionValue.value = LayoutDirection.Ltr
                        }
                        else -> {
                            when(Locale.getDefault().language){
                                "en" -> {
                                    lyricist.languageTag = Locales.EN
                                    layoutDirectionValue.value = LayoutDirection.Ltr
                                }
                                "fr" -> {
                                    lyricist.languageTag =Locales.FR
                                    layoutDirectionValue.value = LayoutDirection.Ltr
                                }
                                "ar" -> {
                                    lyricist.languageTag =Locales.AR
                                    layoutDirectionValue.value = LayoutDirection.Rtl
                                }
                                else -> layoutDirectionValue.value = fromValueToLanguage(languageValue.value,Locale.getDefault().language )
                            }
                        }
                    }
                }
            }



          /*  LaunchedEffect(key1 = layoutDirectionValue ){
                when (languageValue.value) {
                    1 -> {
                        lyricist.languageTag = Locales.AR
                        layoutDirectionValue.value = LayoutDirection.Rtl
                    }
                    2 -> {
                        lyricist.languageTag = Locales.FR
                        layoutDirectionValue.value = LayoutDirection.Ltr
                    }
                    3 -> {
                        lyricist.languageTag = Locales.EN
                        layoutDirectionValue.value = LayoutDirection.Ltr
                    }
                    else -> {
                        when(Locale.getDefault().language){
                            "en" -> {
                                lyricist.languageTag = Locales.EN
                                layoutDirectionValue.value = LayoutDirection.Ltr
                            }
                            "fr" -> {
                                lyricist.languageTag =Locales.FR
                                layoutDirectionValue.value = LayoutDirection.Ltr
                            }
                            "ar" -> {
                                lyricist.languageTag =Locales.AR
                                layoutDirectionValue.value = LayoutDirection.Rtl
                            }
                            else -> layoutDirectionValue.value = fromValueToLanguage(languageValue.value,Locale.getDefault().language )
                        }
                    }
                }
            }*/




            LaunchedEffect(isOnBoardingStart){
                dataStoreRepository.readOnBoardingOption.collect {
                    isOnBoardingStart.value = it
                }
            }




            LaunchedEffect(key1 = themeValue ){
                dataStoreRepository.readThemeSetting.collect {
                    themeValue.value = it
                }
            }

            PowerSHTheme() {
                ProvideStrings(lyricist, LocalStrings) {

                    CompositionLocalProvider(LocalLayoutDirection provides layoutDirectionValue.value) {

                        PowerSHApp(
                            themeValue.value,
                            cartProduct = cartProduct,
                            favouriteProduct = favouriteProduct,
                            orderList = orderList,
                            isOnBoardingStart = isOnBoardingStart
                        )
                    }
                    Timber.d("setContent called")
                }
            }


        }
    }
}

fun fromValueToLanguage(value : Int, defaultLanguage: String): LayoutDirection{
    return when(value){
        -1 ->if (defaultLanguage == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr
        1 -> LayoutDirection.Rtl
        2 -> LayoutDirection.Ltr
        3 -> LayoutDirection.Ltr
        else -> LayoutDirection.Ltr
    }
}
