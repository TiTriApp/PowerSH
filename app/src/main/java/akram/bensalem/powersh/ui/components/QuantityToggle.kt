package akram.bensalem.powersh.ui.components


import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.utils.localization.Locales
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

private enum class QuantityToggleState { Zero, NonZero }

private enum class ImageToggleState { Uploaded, NonUploaded }

@Composable
fun QuantityToggle(
    modifier: Modifier = Modifier,
    isAddedToCart: MutableState<Boolean>,
    onIncrementQuantity: () -> Unit,
    onDecrementQuantity: () -> Unit,
) {

    val transition = updateTransition(
        if (!isAddedToCart.value) QuantityToggleState.Zero else QuantityToggleState.NonZero,
        label = ""
    )

    val backgroundColor by transition.animateColor { state ->
        when (state) {
            QuantityToggleState.Zero -> MaterialTheme.colors.primary
            QuantityToggleState.NonZero -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            QuantityToggleState.Zero -> MaterialTheme.colors.primary
            QuantityToggleState.NonZero -> LocalContentColor.current
        }
    }

    val iconSize by transition.animateDp { state ->
        when (state) {
            QuantityToggleState.Zero -> 0.dp
            QuantityToggleState.NonZero -> 18.dp
        }
    }


//
    Button(
        border = BorderStroke(2.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .padding(end = 8.dp),
        onClick = {
            isAddedToCart.value = !isAddedToCart.value
            if (isAddedToCart.value) {
                onIncrementQuantity()
            } else {
                onDecrementQuantity()
            }

        }) {


        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isAddedToCart.value) {

                Icon(
                    Icons.Rounded.Done,
                    contentDescription = LocalStrings.current.addedToCart,
                    tint = if (!isAddedToCart.value) Color.White else MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .size(iconSize)
                                                    .graphicsLayer {
                                                        rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                    },

                )
            }
            Text(
                text = if (!isAddedToCart.value) LocalStrings.current.addedToCart else LocalStrings.current.added,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
                color = if (!isAddedToCart.value) Color.White else MaterialTheme.colors.primary,
                fontStyle = FontStyle.Normal,
                fontWeight = if (!isAddedToCart.value) FontWeight.Normal else FontWeight.Medium,
                fontSize = 13.sp,
                modifier = Modifier
                    .animateContentSize()
                    .padding(
                        start = if (!isAddedToCart.value) 10.dp else 0.dp,
                        end = 10.dp,
                        top = 0.dp,
                        bottom = 0.dp
                    )
            )
        }
    }
}


@Composable
fun UploadImageToggle(
    modifier: Modifier = Modifier,
    isImageSelected: Boolean,
    onAddClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
) {

    val transition = updateTransition(
        if (!isImageSelected) ImageToggleState.NonUploaded else ImageToggleState.Uploaded,
        label = ""
    )

    val backgroundColor by transition.animateColor { state ->
        when (state) {
            ImageToggleState.NonUploaded -> MaterialTheme.colors.primary
            ImageToggleState.Uploaded -> MaterialTheme.colors.surface
        }
    }

    val contentColor by transition.animateColor { state ->
        when (state) {
            ImageToggleState.NonUploaded -> MaterialTheme.colors.primary
            ImageToggleState.Uploaded -> LocalContentColor.current
        }
    }

    val iconSize by transition.animateDp { state ->
        when (state) {
            ImageToggleState.NonUploaded -> 0.dp
            ImageToggleState.Uploaded -> 18.dp
        }
    }


//
    Button(
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
        ),
        shape = RoundedCornerShape(14.dp),
        modifier = modifier
            .padding(end = 8.dp),
        onClick = {
            if (isImageSelected) {
                onRemoveClicked()
            } else {
                onAddClicked()
            }

        }) {


        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (isImageSelected) {

                Icon(
                    Icons.Rounded.Done,
                    contentDescription = "Uploaded",
                    tint = if (!isImageSelected) Color.White else MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(
                            start = Dimens.MiniSmallPadding.size,
                            end = Dimens.MiniSmallPadding.size,
                            top = 0.dp,
                            bottom = 0.dp
                        )
                        .size(iconSize)
                                                    .graphicsLayer {
                                                        rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                                                    },

                )
            }
            Text(
                text = if (!isImageSelected)  LocalStrings.current.upload else LocalStrings.current.uploaded,
                style = MaterialTheme.typography.button,
                textAlign = TextAlign.Center,
                color = if (!isImageSelected) Color.White else MaterialTheme.colors.primary,
                fontStyle = FontStyle.Normal,
                fontWeight = if (!isImageSelected) FontWeight.Normal else FontWeight.Medium,
                fontSize = 11.sp,
                modifier = Modifier
                    .animateContentSize()
                    .padding(
                        start = if (!isImageSelected) Dimens.MiniSmallPadding.size else 0.dp,
                        end = Dimens.MiniSmallPadding.size,
                        top = 0.dp,
                        bottom = 0.dp
                    )
            )
        }
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ColorContentDialog(
    visible: MutableState<Boolean>,
    onColorSelected: (String) -> Unit
) {

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
    ) {


        if (visible.value) {
            ContentDialog(
                title = LocalStrings.current.selectFavouriteColor,
                onDismissRequest = { visible.value = false },
            ) {
                Grid(
                    list = mColorsIdsGridList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) { id ->
                    colorGridItem(
                        id = id,
                        onSelected = {
                            onColorSelected(it)
                            visible.value = false
                        }
                    )

                }
            }
        }


    }

}


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun sizeContentDialog(
    visible: MutableState<Boolean>,
    onSizeSelected: (Int) -> Unit
) {

    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)),
    ) {


        if (visible.value) {
            ContentDialog(
                title = LocalStrings.current.selectFavouriteSize,
                onDismissRequest = { visible.value = false },
            ) {
                Grid(
                    list = mSizeIdGridList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) { id ->

                    sizeGridItem(
                        id = id,
                        onSelected = {
                            onSizeSelected(it)
                            visible.value = false
                        }
                    )

                }
            }
        }


    }

}


@Composable
fun <T> Grid(
    list: List<List<T?>>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (item: T?) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        for (row in list) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (item in row) {
                    itemContent(item)
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentDialog(
    title: String,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = onDismissRequest
                )
        ) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6.copy(fontSize = 18.sp),
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )

                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )

                    content()
                }
            }
        }
    }
}











