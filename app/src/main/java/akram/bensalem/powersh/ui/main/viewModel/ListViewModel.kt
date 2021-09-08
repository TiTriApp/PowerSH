package akram.bensalem.powersh.ui.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import akram.bensalem.powersh.data.dataTransferObjects.asDomainModel
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.repository.DataStoreRepository
import akram.bensalem.powersh.repository.PowerSHRepository
import akram.bensalem.powersh.ui.main.screenStates.MainListScreenState
import akram.bensalem.powersh.utils.Resource
import akram.bensalem.powersh.utils.getChipNamesList
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val powerSHRepository: PowerSHRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val allThinkpads = MutableStateFlow<List<ShoeProduct>>(listOf())
    private val availableThinkpadSeries = MutableStateFlow<List<String>>(listOf())
    private val networkLoading = MutableStateFlow(false)
    private val networkError = MutableStateFlow("")
    private val sortOption = MutableStateFlow(0)

    // This will combine the different Flows emitted in this ViewModel into a single state
    // that will be observed by the UI.
    // Compose mutableStateOf can also be used to provide something similar
    val uiState: StateFlow<MainListScreenState> = combine(
        allThinkpads,
        networkLoading,
        networkError,
        availableThinkpadSeries,
        sortOption
    ) { allThinkpads, networkLoading, networkError, availableThinkpadSeries, sortOption ->
        MainListScreenState.MainListScreen(
            shoeProductList = allThinkpads,
            networkLoading = networkLoading,
            networkError = networkError,
            thinkpadSeriesList = availableThinkpadSeries,
            sortOption = sortOption
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainListScreenState.Empty
    )

    init {
        refreshThinkpadList()
        getUserSortOption()
        //getNewThinkpadListFromDatabase()
    }

    // Retrieves new data from the network and inserts it into the database
    // Also used by pull down to refresh.
    fun refreshThinkpadList() {
        viewModelScope.launch {
            networkLoading.value = true

            Timber.d("loading ThinkpadList")
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

    // Get the user's preferred Sorting option first the load data from the database
    private fun getUserSortOption() {
        viewModelScope.launch {
            dataStoreRepository.readSortOptionSetting.collect { sortValue ->
                sortOption.value = sortValue
                getNewThinkpadListFromDatabase()
            }
        }
    }

    // Makes sure fresh data is taken from the database even after a network refresh
    // Also takes search query and returns only the data needed
    fun getNewThinkpadListFromDatabase(query: String = "") {
        viewModelScope.launch {
            when (sortOption.value) {
                0 -> {
                    powerSHRepository.getShoesAlphaAscending(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                            if (allThinkpads.value.size > availableThinkpadSeries.value.size) {
                                availableThinkpadSeries.value =
                                    allThinkpads.value.getChipNamesList()
                            }
                        }
                }
                1 -> {
                    powerSHRepository.getShoesNewestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                2 -> {
                    powerSHRepository.getShoesOldestFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                3 -> {
                    powerSHRepository.getShoesLowPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
                4 -> {
                    powerSHRepository.getShoesHighPriceFirst(query)
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                        }
                }
            }
        }
    }


    fun getNewShoesListFromDatabase(index: Int = 0){
        viewModelScope.launch {
            when(sortOption.value){
                0 -> {
                    powerSHRepository.getShoesAlphaAscending(index.toString())
                        .collect {
                            allThinkpads.value = it.asDomainModel()
                            if (allThinkpads.value.size > availableThinkpadSeries.value.size) {
                                availableThinkpadSeries.value =
                                    allThinkpads.value.getChipNamesList()
                            }
                        }
                }
                1 -> { }
                2 -> { }
                3 -> { }
                4 -> { }
            }
        }
    }

    // Set the sort option from the UI
    fun sortSelected(sort: Int) {
        sortOption.value = sort
        getNewThinkpadListFromDatabase()
    }
}