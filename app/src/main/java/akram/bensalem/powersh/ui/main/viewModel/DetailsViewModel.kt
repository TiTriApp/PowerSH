package akram.bensalem.powersh.ui.main.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import akram.bensalem.powersh.data.dataTransferObjects.asThinkpad
import akram.bensalem.powersh.repository.PowerSHRepository
import akram.bensalem.powersh.ui.main.screenStates.DetailsScreenState
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val powerSHRepository: PowerSHRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val thinkpadName = savedStateHandle.get<String>("thinkpad")!!

    private val _uiState = MutableStateFlow<DetailsScreenState>(
        value = DetailsScreenState.EmptyState
    )
    val uiState: StateFlow<DetailsScreenState> = _uiState

    init {
        initializeThinkpad()
    }

    private fun initializeThinkpad() {
        viewModelScope.launch {
            powerSHRepository.getShoe(thinkpadName).collect {
                _uiState.value = DetailsScreenState.Detail(it.asThinkpad())
            }
        }
    }


}
