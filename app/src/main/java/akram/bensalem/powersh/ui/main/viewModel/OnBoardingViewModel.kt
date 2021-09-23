package akram.bensalem.powersh.ui.main.viewModel

import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.utils.OnBoardingProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(false)
    val viewState: StateFlow<Boolean>
        get() = _state

    fun getStartedClick() {
        viewModelScope.launch {
            dataStoreRepository.saveOnBoardingStart(false)
            _state.value = true
            saveOnBoarding(false)
        }
    }


    fun saveOnBoarding(startOnbording: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveOnBoardingStart(value = startOnbording)
            Timber.d("StartOnbording Data Saved $startOnbording")
        }
    }

    fun getOnBoardingItemsList() = OnBoardingProvider.onBoardingDataList
}