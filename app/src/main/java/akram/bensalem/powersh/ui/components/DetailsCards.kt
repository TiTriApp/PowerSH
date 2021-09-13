package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.data.model.CardItem
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.Constants

@Composable
fun DetailsCards(
    modifier: Modifier = Modifier,
    shoeProduct: ShoeProduct,
    cartProduct: MutableList<CardItem>,
    favourite: MutableState<Boolean>,
    onfavouriteClick: () -> Unit = { },
    onNavigateToCartScreen: () -> Unit,
) {

    val quantity = remember {
        mutableStateOf(1)
    }


    val isColorDialogVisible = remember {
        mutableStateOf(false)
    }

    val isAddedToCart = remember {
        mutableStateOf(false)
    }


    val colorSelected = remember {
        mutableStateOf("Black")
    }

    val isSizeDialogVisible = remember {
        mutableStateOf(false)
    }
    val sizeSelected = remember {
        mutableStateOf(40)
    }





    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShoeDetails(
            shoeProduct = shoeProduct,
            onfavouriteClick = onfavouriteClick,
            favourite = favourite,
        )
        ShoeFeatures(
            shoeProduct = shoeProduct,
            cartProduct = cartProduct,
            quantity = quantity,
            isColorDialogVisible = isColorDialogVisible,
            colorSelected = colorSelected,
            isSizeDialogVisible = isSizeDialogVisible,
            sizeSelected = sizeSelected,
            isAddedToCart = isAddedToCart,
            onNavigateToCartScreen = onNavigateToCartScreen
        )

        ColorContentDialog(
            visible = isColorDialogVisible,
            onColorSelected = {
                colorSelected.value = it
            }
        )
        sizeContentDialog(
            visible = isSizeDialogVisible,
            onSizeSelected = {
                sizeSelected.value = it
            }
        )

    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailsCardsPreview() {
    val favourite = remember {
        mutableStateOf(false)
    }
    val cartProduct = remember { Constants.cartList }


    PowerSHTheme {
        DetailsCards(
            shoeProduct = Constants.ShoesListPreview[0],
            cartProduct = cartProduct,
            favourite = favourite,
            onNavigateToCartScreen = {},
        )
    }
}
