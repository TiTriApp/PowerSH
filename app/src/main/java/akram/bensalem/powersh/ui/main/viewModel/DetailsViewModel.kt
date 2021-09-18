package akram.bensalem.powersh.ui.main.viewModel

import akram.bensalem.powersh.data.dataTransferObjects.asPowerSHshoes
import akram.bensalem.powersh.repository.PowerSHRepository
import akram.bensalem.powersh.ui.main.screenStates.DetailsScreenState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val powerSHRepository: PowerSHRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val shoeName = savedStateHandle.get<String>("shoe")!!

    private val _uiState = MutableStateFlow<DetailsScreenState>(
        value = DetailsScreenState.EmptyState
    )
    val uiState: StateFlow<DetailsScreenState> = _uiState

    init {
        initialize()
    }

    private fun initialize() {
        viewModelScope.launch {
            powerSHRepository.getShoe(shoeName).collect {
                _uiState.value = DetailsScreenState.Detail(it.asPowerSHshoes())
            }
        }
    }


}
