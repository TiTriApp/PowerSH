package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.utils.localization.Locales
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun radioPaymentOption(
    selected: MutableState<String>,
    id: String,
    title: String,
    icon: ImageVector,
    contentDescription: String = "",
    detail: String,
) {
    Row(
        modifier = Modifier
            .padding(8.dp, 8.dp)
            .clickable(onClick = { selected.value = id })
    ) {
        RadioButton(
            selected = selected.value == id,
            onClick = { selected.value = id },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary,
                unselectedColor = MaterialTheme.colors.onSurface
            ),
            modifier = Modifier
                .padding(start = 0.dp, end = 8.dp)

        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = { selected.value = id })
        ) {
            Text(
                text = title,
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = { selected.value = id })
                    .padding(start = 4.dp)
            )
            Text(
                text = detail,
                color = MaterialTheme.colors.onSurface,
                fontSize = 12.sp,
                modifier = Modifier
                    .clickable(onClick = { selected.value = id })
                    .padding(start = 4.dp)
            )
        }

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (selected.value == id) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.CenterVertically)
                                        .graphicsLayer {
                                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                        },
        )

    }
}