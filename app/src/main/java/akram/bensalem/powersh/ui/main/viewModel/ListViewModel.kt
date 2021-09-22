package akram.bensalem.powersh.ui.main.viewModel

import akram.bensalem.powersh.data.dataTransferObjects.asDomainModel
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.repository.PowerSHRepository
import akram.bensalem.powersh.ui.main.screenStates.MainListScreenState
import akram.bensalem.powersh.utils.Resource
import akram.bensalem.powersh.utils.getChipNamesList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val powerSHRepository: PowerSHRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val allShoes = MutableStateFlow<List<ShoeProduct>>(listOf())
    private val availableShoes = MutableStateFlow<List<String>>(listOf())
    private val networkLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow("")
    private val sortOption = MutableStateFlow(0)


    val uiState: StateFlow<MainListScreenState> = combine(
        allShoes,
        networkLoading,
        networkError,
        availableShoes,
        sortOption
    ) { allShoes1, networkLoading, networkError, availableShoes, sortOption ->
        MainListScreenState.MainListScreen(
            shoeProductList = allShoes1,
            networkLoading = networkLoading,
            networkError = networkError,
            seriesList = availableShoes,
            sortOption = sortOption
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(0),
        initialValue = MainListScreenState.Empty
    )

    init {
      //  refreshShoesList()
      //  getUserSortOption()
    }

    fun refreshShoesList() {
        viewModelScope.launch {
            networkLoading.value = true

            Timber.d("loading Shoes List")
            when (val result = powerSHRepository.getAllShoesFromNetwork()) {
                is Resource.Success -> {
                    powerSHRepository.refreshShoesList(result.data!!)
                    networkLoading.value = false
                }
                is Resource.Error -> {
                    networkLoading.value = false
                    networkError.value = result.message!!
                }
            }
        }
    }

    private fun getUserSortOption() {
        viewModelScope.launch {
            dataStoreRepository.readSortOptionSetting.collect { sortValue ->
                sortOption.value = sortValue
                getNewShoesListFromDatabase(index = 0)
            }
        }
    }


    fun getNewShoesListFromDatabase(query: String = "", index: Int) {
        viewModelScope.launch {
            when (sortOption.value) {
                0 -> {
                    powerSHRepository.getShoesAlphaAscending(query = query, index = index)
                        .collect {
                            allShoes.value = it.asDomainModel()
                            if (allShoes.value.size > availableShoes.value.size) {
                                availableShoes.value =
                                    allShoes.value.getChipNamesList()
                            }
                        }
                }
                1 -> {
                    powerSHRepository.getShoesNewestFirst(query, index = index)
                        .collect {
                            allShoes.value = it.asDomainModel()
                        }
                }
                2 -> {
                    powerSHRepository.getShoesOldestFirst(query, index = index)
                        .collect {
                            allShoes.value = it.asDomainModel()
                        }
                }
                3 -> {
                    powerSHRepository.getShoesLowPriceFirst(query, index = index)
                        .collect {
                            allShoes.value = it.asDomainModel()
                        }
                }
                4 -> {
                    powerSHRepository.getShoesHighPriceFirst(query, index = index)
                        .collect {
                            allShoes.value = it.asDomainModel()
                        }
                }
            }
        }
    }


    fun sortSelected(sort: Int, index: Int) {
        sortOption.value = sort
        getNewShoesListFromDatabase(index = index)
    }
}