package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.R
import akram.bensalem.powersh.lyricist
import akram.bensalem.powersh.ui.components.AboutTopSection
import akram.bensalem.powersh.ui.components.CollapsingToolbarBase
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.localization.Locales
import akram.bensalem.powersh.utils.rememberMapViewWithLifecycle
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.navigationBarsPadding
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    onCheckUpdates: () -> Unit = { },
    onBackButtonPressed: () -> Unit = { }
) {

    val mapView = rememberMapViewWithLifecycle()
    val localString = LocalStrings.current


    // CollapsingToolbar Implementation
    val toolbarHeight = 300.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = localString.aboutUs,
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = onBackButtonPressed
            ) {
                AboutTopSection(
                    appName = stringResource(id = R.string.app_name),
                    version = "1.0.0",
                    appLogo = painterResource(id = R.drawable.big_circle_powersh),
                    onCheckUpdatesClicked = onCheckUpdates
                )
            }
        },
    ) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection),
            state = listState
        ) {

            item {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .graphicsLayer {
                            rotationY = if (lyricist.languageTag == Locales.AR) 180f else 0f
                        },
                    painter = painterResource(id = R.drawable.ic_about),
                    contentDescription = LocalStrings.current.appLogo
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.MediumPadding.size)
                ) {
                    Text(
                        LocalStrings.current.projectMadeBy,
                        color = MaterialTheme.colors.onBackground,
                        style = MaterialTheme.typography.body1,
                    )
                    Text(
                        LocalStrings.current.akramBensalem,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = Dimens.MiniSmallPadding.size)
                    )
                }

            }

            item {
                AndroidView(
                    modifier = Modifier.fillMaxWidth()
                             .height(260.dp)
                             .padding(Dimens.LargePadding.size, Dimens.MediumPadding.size)
                             .background(Color.Transparent, RoundedCornerShape(12.dp))
                             .shadow(elevation = 2.dp,shape = RoundedCornerShape(12.dp))
                             .border(width =1.dp, color = MaterialTheme.colors.onSurface,shape = RoundedCornerShape(12.dp))
                    ,
                    factory= { mapView}) {mapView->
                    CoroutineScope(Dispatchers.Main).launch {
                        val map = mapView.awaitMap()
                        map.uiSettings.isZoomControlsEnabled = true
                        val pickUp =  LatLng(36.25682, 2.78190)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUp,10f))
                        val markerOptions = MarkerOptions()
                            .title(localString.akramBensalem)
                            .position(pickUp)
                        map.addMarker(markerOptions)

                    }
                }
            }
            item{
                Text(
                    text = LocalStrings.current.madeByAkramBensalem,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SmallPadding.size)
                        .navigationBarsPadding(),
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onSurface
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }

    }
}

@Preview
@Composable
private fun AboutScreenPrev() {
    PowerSHTheme {
        AboutScreen()
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AboutScreenNightPreview() {
    PowerSHTheme {
        AboutScreen()
    }
}

