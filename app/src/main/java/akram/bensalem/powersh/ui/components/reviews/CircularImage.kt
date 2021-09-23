package akram.bensalem.powersh.ui.components.reviews

import akram.bensalem.powersh.ui.components.surfaces.PowerSHSurface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi

import coil.compose.rememberImagePainter


@ExperimentalCoilApi
@Composable
fun CircularAppImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {


    var imageLoading by remember {
        mutableStateOf(true)
    }

    val coilPainter = rememberImagePainter(
        data = imageUrl,
        builder = {
            crossfade(true)
            listener(
                onStart = {
                    imageLoading = true
                },
                onSuccess = { _, _ ->
                    imageLoading = false
                }
            )
        }
    )


    PowerSHSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = coilPainter,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
    }
}