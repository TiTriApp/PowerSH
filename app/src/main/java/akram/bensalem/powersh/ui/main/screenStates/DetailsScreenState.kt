package akram.bensalem.powersh.ui.main.screenStates

import akram.bensalem.powersh.data.model.ShoeProduct

sealed class DetailsScreenState {
    data class Detail(val shoeProduct: ShoeProduct) : DetailsScreenState()
    object EmptyState : DetailsScreenState()
}