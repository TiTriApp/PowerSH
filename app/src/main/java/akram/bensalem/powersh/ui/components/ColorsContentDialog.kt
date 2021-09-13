package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.ui.main.screenStates.*
import akram.bensalem.powersh.ui.theme.CardCoverPink
import akram.bensalem.powersh.ui.theme.MainCardList
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



// Grid columns
private const val cols = 4

val mColorsIdsGridList = ColorIds.toGridList(cols)
val mSizeIdGridList = SizeIds.toGridList(cols)



@Composable
fun colorGridItem(
    id: String?,
    size: Dp = 45.dp,
    onSelected: (String) -> Unit
) {
    resIdFor(id)?.let {

        IconButton(
            modifier = Modifier
                .background(
                    color = it,
                    shape = CircleShape
                )
                .size(size)
            ,
            onClick = {  id?.let(onSelected)  },
        ) {

        }

    }

}



@Composable
fun sizeGridItem(
    id: String?,
    size: Dp = 45.dp,
    onSelected: (Int) -> Unit
) {
    sizeIdFor(id)?.let {

        IconButton(
            modifier = Modifier
                .padding(2.dp)
                .background(
                    color = MainCardList,
                    shape = CircleShape
                )
                .border(width = 2.dp, color = CardCoverPink, shape  = CircleShape)
                .size(size)
            ,
            onClick = {  onSelected(it)},
        ) {
          Text(
              text = it.toString(),
              color = Color.DarkGray,
              fontStyle = FontStyle.Normal,
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp,
              textAlign = TextAlign.Center,
              modifier = Modifier
                  .padding(4.dp)
          )
        }

    }

}

