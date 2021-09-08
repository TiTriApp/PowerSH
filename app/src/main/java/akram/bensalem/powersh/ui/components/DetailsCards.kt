package akram.bensalem.powersh.ui.components

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
    onExternalLinkClick: () -> Unit = { }
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThinkpadDetails(
            shoeProduct = shoeProduct,
            onExternalLinkClick = onExternalLinkClick
        )
        ThinkpadFeatures(shoeProduct = shoeProduct)
    }

}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DetailsCardsPreview() {
    PowerSHTheme {
        DetailsCards(shoeProduct = Constants.ShoesListPreview[0])
    }
}
