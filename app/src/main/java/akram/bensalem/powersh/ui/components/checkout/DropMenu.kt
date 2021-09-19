package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.theme.Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import java.text.NumberFormat


@Composable
fun wilayaDropDownMenu(
    modifier: Modifier,
    items: SnapshotStateList<String>,
    wilayaSelectedIndex: MutableState<Int>,
    dairaSelectedIndex: MutableState<Int>,
    communeSelectedIndex: MutableState<Int>,
) {

    var expanded by remember { mutableStateOf(false) }
    val f: NumberFormat = DecimalFormat("00")


    Box(
        modifier = modifier
            .padding(Dimens.MiniSmallPadding.size, 8.dp)
            .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
            .background(color =MaterialTheme.colors.surface , shape =RoundedCornerShape(12.dp) )

    ) {
        Text(
            text = if (wilayaSelectedIndex.value != 0) "${f.format(wilayaSelectedIndex.value)}- ${items[wilayaSelectedIndex.value]}" else LocalStrings.current.wilaya,
            color = if (wilayaSelectedIndex.value != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface ,maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    communeSelectedIndex.value = 0
                    dairaSelectedIndex.value = 0
                    expanded = false
                    wilayaSelectedIndex.value = index
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index != 0) "${f.format(index)}- ${s}" else s,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (index != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun diaraDropDownMenu(
    modifier: Modifier,
    items: SnapshotStateList<String>,
    selectedIndex: MutableState<Int>,
    communeSelectedIndex: MutableState<Int>
) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = modifier
            .padding(Dimens.MiniSmallPadding.size, 8.dp)
            .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
            .background(color =MaterialTheme.colors.surface , shape =RoundedCornerShape(12.dp) )

    ) {


        Text(
            text = if (selectedIndex.value != 0) items[selectedIndex.value] else LocalStrings.current.daira,
            color = if (selectedIndex.value != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface ,maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    communeSelectedIndex.value = 0
                    expanded = false
                    selectedIndex.value = index
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index != 0) s else LocalStrings.current.daira,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (index != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun communeDropDownMenu(
    modifier: Modifier,
    items: SnapshotStateList<String>,
    selectedIndex: MutableState<Int>) {

    var expanded by remember { mutableStateOf(false) }


    Box(
        modifier = modifier
            .padding(Dimens.MiniSmallPadding.size, 8.dp)
            .border(1.dp, MaterialTheme.colors.onBackground, RoundedCornerShape(12.dp))
            .background(color =MaterialTheme.colors.surface , shape =RoundedCornerShape(12.dp) )

    ) {
        Text(
            text = if (selectedIndex.value != 0) items[selectedIndex.value] else LocalStrings.current.commune,
            color = if (selectedIndex.value != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface ,maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier =
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .clickable(onClick = { expanded = true })
                .padding(16.dp, 4.dp)
        )
        IconButton(
            onClick = {
                expanded = true
            },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.ArrowDropDown,
                contentDescription = "Open drop down menu",
                tint = Color.DarkGray
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex.value = index
                    expanded = false
                }) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index != 0) s else LocalStrings.current.commune,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (index != 0) MaterialTheme.colors.onBackground else MaterialTheme.colors.onSurface
                        )
                    }

                }
            }
        }
    }
}
