package akram.bensalem.powersh.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.ui.main.screenStates.SettingsScreenState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val themeOptionValue = MutableStateFlow(-1)
    private val sortOptionValue = MutableStateFlow(0)

    // Combining Flows from different points to be displayed on the screen
    // The state is only shared when the subscriber is active.
    // In this case it's the whenever the settings screen is active.
    // And sharing will stop after 5 seconds from when the subscriber stops being active.
    val uiState: StateFlow<SettingsScreenState> = combine(
        themeOptionValue,
        sortOptionValue
    ) { themeOptionValue, sortOptionValue ->
        Timber.d("ThemeOption State: $themeOptionValue")
        Timber.d("Sort Option: $sortOptionValue")
        SettingsScreenState.Settings(
            themeOption = themeOptionValue,
            sortOption = sortOptionValue
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsScreenState.DefaultState
    )

    fun saveThemeSetting(themeValue: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveThemeSetting(value = themeValue)
            Timber.d("Theme Data Saved $themeValue")
        }
    }

    fun saveSortOptionSetting(sortValue: Int) {
        viewModelScope.launch {
            dataStoreRepository.saveSortOptionSetting(value = sortValue)
            Timber.d("Sort Data Saved: $sortValue")
        }
    }

    init {
        readSettings()
    }

    // This is used retrieve the previous set values for settings and be displayed on the
    // settings screen.
    private fun readSettings() {

        // Different Flows can't share the same CoroutineScope when data is being collected
        // They must run on different Scopes to collect the latest data
        viewModelScope.launch {
            dataStoreRepository.readThemeSetting.collect { themeValue ->
                Timber.d("Theme Data Changed $themeValue")
                themeOptionValue.value = themeValue
            }
        }
        viewModelScope.launch {
            dataStoreRepository.readSortOptionSetting.collect { sortValue ->
                Timber.d("Sort Data Changed $sortValue")
                sortOptionValue.value = sortValue
            }
        }
    }
}