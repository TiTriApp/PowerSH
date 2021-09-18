package akram.bensalem.powersh.ui.main.screenStates

import akram.bensalem.powersh.data.model.ShoeProduct

sealed class MainListScreenState {
    data class MainListScreen(
        val shoeProductList: List<ShoeProduct> = listOf(),
        val networkLoading: Boolean = false,
        val networkError: String = "",
        val seriesList: List<String> = listOf(),
        var sortOption: Int = 0
    ) : MainListScreenState()

    companion object {
        val Empty = MainListScreen()
    }
}
