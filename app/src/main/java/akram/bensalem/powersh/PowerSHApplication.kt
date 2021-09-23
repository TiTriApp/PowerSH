package akram.bensalem.powersh

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import akram.bensalem.powersh.repository.DataStoreRepository
import android.content.res.Configuration
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class PowerSHApplication: Application() {
    @Inject lateinit var dataStoreRepository: DataStoreRepository
    override fun onCreate() {
//        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
//        scope.launch {
//            dataStoreRepository.readThemeSetting.collect {
//               AppCompatDelegate.setDefaultNightMode(it)
//            }
//        }


        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}