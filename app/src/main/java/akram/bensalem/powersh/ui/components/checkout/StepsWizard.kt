package akram.bensalem.powersh.ui.components.checkout

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.data.model.Step
import akram.bensalem.powersh.utils.Constants
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StepsWizard(
    step: MutableState<Int>,
) {

    val localStrings = LocalStrings.current

    val steps = remember { mutableStateListOf<Step>(
        Step(
            title = localStrings.paymentWithout,
        ),
        Step(
            title = localStrings.addressWithout,
        ),
        Step(
            title = localStrings.confirmation,
        ),
    )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 0.dp, bottom = 16.dp, top = 0.dp)
    ) {

        steps.forEachIndexed { index, s ->
            StepItem(
                s,
                index,
                steps.size - 1,
                step.value,
                Modifier.weight(1f),
            )
        }
    }

}