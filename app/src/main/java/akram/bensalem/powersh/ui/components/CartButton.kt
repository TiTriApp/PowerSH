package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.ui.theme.Amber500
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun CartButton(
    quantity: Int,
    price: Double,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = Amber500,
        contentColor = Color.DarkGray,
        elevation = 16.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text =
                    if (quantity == 1)
                        "$quantity Shoes"
                    else
                        "$quantity Shoes",
                    color = Color.DarkGray,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.button
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$" + "%.2f".format(price),
                    color = Color.DarkGray,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.button
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "VIEW CART",
                color = Color.DarkGray,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.button
            )

            Icon(imageVector = Icons.Rounded.ChevronRight, contentDescription = "Icon")
        }
    }
}

@Preview("MenuCartButton")
@Composable
private fun MenuCartButtonPreview() {
    PowerSHTheme{
        CartButton(
            quantity = 3,
            price = 0.0,
            onClick = {}
        )
    }
}

