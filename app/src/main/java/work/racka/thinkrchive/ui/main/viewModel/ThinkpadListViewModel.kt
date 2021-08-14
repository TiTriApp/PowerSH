package work.racka.thinkrchive.ui.main.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import work.racka.thinkrchive.data.dataTransferObjects.asDomainModel
import work.racka.thinkrchive.data.model.Thinkpad
import work.racka.thinkrchive.repository.ThinkpadRepository
import work.racka.thinkrchive.utils.Resource
import javax.inject.Inject

@HiltViewModel
class ThinkpadListViewModel @Inject constructor(
    private val thinkpadRepository: ThinkpadRepository
) : ViewModel() {

    var thinkpadList by mutableStateOf<List<Thinkpad>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        refreshThinkpadList()
        getThinkpadListFromDatabase()
    }

    fun refreshThinkpadList() {
        viewModelScope.launch {
            isLoading.value = true
            Timber.d("loading ThinkpadList")
            when (val result = thinkpadRepository.getAllThinkpadsFromNetwork()) {
                is Resource.Success -> {
                    thinkpadRepository.refreshThinkpadList(result.data!!)
                    isLoading.value = false
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    private fun getThinkpadListFromDatabase() {
        viewModelScope.launch {
            thinkpadRepository.getAllThinkpads().collect {
                thinkpadList = it.asDomainModel()
            }
        }
    }
}