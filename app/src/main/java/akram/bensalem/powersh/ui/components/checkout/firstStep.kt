package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.ui.main.screens.MainPayOptions
import akram.bensalem.powersh.ui.main.screens.displayRadioGroup
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi


@ExperimentalPermissionsApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun firstStep(
    visible: Boolean,
    selected: MutableState<String>,
    totalPrice: MutableState<Int>,
    imageUri : MutableState<Uri?> = remember {
        mutableStateOf<Uri?>(null)
    },
    onCallUsClicked: () -> Unit = {},
    onUploadImageClicked : () -> Unit = {}
) {




    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri.value = uri
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInHorizontally(),
        exit = fadeOut() + slideOutHorizontally(
            targetOffsetX ={ it },
            animationSpec=  spring()
        ),
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            displayRadioGroup(selected = selected)
            cashOption(visible = selected.value == MainPayOptions.CASH_OPTION ,
                price = totalPrice.value,
                onCallUsClicked = onCallUsClicked,
                modifier = Modifier.weight(1f))
            ccpOption(visible = selected.value == MainPayOptions.CCP_OPTION ,
                price = totalPrice.value,
                imageUri = imageUri.value,
                onUploadImageClicked = {
                    launcher.launch("image/*")
                },
                onRemoveImageClicked = {
                    imageUri.value = null
                },
                modifier = Modifier.weight(1f))
        }
    }
}