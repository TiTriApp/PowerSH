package akram.bensalem.powersh.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import akram.bensalem.powersh.utils.Constants
import androidx.datastore.preferences.core.booleanPreferencesKey
import java.io.IOException

@ActivityScoped
class DataStoreRepository(
    private val context: Context
) {
    private object PreferenceKeys {
        val themeOption = intPreferencesKey(name = "theme_option")
        val sortOption = intPreferencesKey(name = "sort_option")
        val languageOption = intPreferencesKey(name = "language_option")
        val startMainPage = booleanPreferencesKey(name = "start_main_page")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = Constants.PREFERENCE_NAME
    )

    suspend fun saveThemeSetting(value: Int) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.themeOption] = value
        }
    }


    suspend fun saveLanguageSetting(value: Int) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.languageOption] = value
        }
    }

    suspend fun saveOnBoardingStart(value: Boolean) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.startMainPage] = value
        }
    }



    val readOnBoardingOption: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.startMainPage] ?: false
        }


    val readThemeSetting: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.themeOption] ?: -1
        }



    val readLanguageSetting: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.languageOption] ?: -1
        }



    suspend fun saveSortOptionSetting(value: Int) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.sortOption] = value
        }
    }

    val readSortOptionSetting: Flow<Int> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.d(exception.message.toString())
            } else {
                throw exception
            }
        }.map { settings ->
            settings[PreferenceKeys.sortOption] ?: 0
        }
}
