package akram.bensalem.powersh.ui.components

import akram.bensalem.powersh.LocalStrings
import akram.bensalem.powersh.ui.theme.*
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.Language
import akram.bensalem.powersh.utils.Sort
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun SettingSheetLayout(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    sheetTitle: String,
    content: @Composable () -> Unit
) {

    Surface(
        shape = BottomSheetShape,
        color = MaterialTheme.colors.surface,
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.SmallPadding.size)
                .navigationBarsPadding()
        ) {
            TopSheetSection(
                modifier = Modifier
                    .padding(Dimens.SmallPadding.size),
                sheetTitle = sheetTitle,
                onCloseClicked = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
                settingsButtonVisible = false
            )
            content()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SettingEntrySheet(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    settingsEntryName: String,
    currentSortOption: Int,
    onSortOptionClicked: (Int) -> Unit = { },
    currentTheme: Int,
    currentLanguageOption: Int,
    onThemeOptionClicked: (Int) -> Unit = { },
    onLanguageOptionClicked: (Int) -> Unit = {}
) {

    val localStrings = LocalStrings.current
    SettingSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        scope = scope,
        sheetTitle = settingsEntryName
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            when (settingsEntryName) {
                localStrings.themeOptions -> {
                    items(Theme.values()) { item ->
                        val selectedColor by animateColorAsState(
                            targetValue = if (currentTheme == item.themeValue) {
                                MaterialTheme.colors.primary
                                    .copy(alpha = .6f)
                            } else Color.Transparent,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )
                        val contentColor by animateColorAsState(
                            targetValue = if (currentTheme == item.themeValue) {
                                LightDark
                                    .copy(alpha = .9f)
                            } else MaterialTheme.colors.onBackground,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )

                        SheetOption(
                            modifier = Modifier
                                .fillMaxWidth(),
                            sortOptionName = when(item.themeName){
                                Theme.LIGHT_THEME.themeName -> LocalStrings.current.lightTheme
                                Theme.DARK_THEME.themeName -> LocalStrings.current.darkTheme
                                else -> LocalStrings.current.followSystemMode
                            },
                            icon = item.icon,
                            onOptionClicked = {
                                onThemeOptionClicked(item.themeValue)
                            },
                            selectedSortColor = selectedColor,
                            contentColor = contentColor
                        )
                    }
                }
                localStrings.sortOptions -> {
                    items(Sort.values()) { item ->
                        val selectedColor by animateColorAsState(
                            targetValue = if (currentSortOption == item.sortValue) {
                                MaterialTheme.colors.primary
                                    .copy(alpha = .6f)
                            } else Color.Transparent,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )
                        val contentColor by animateColorAsState(
                            targetValue = if (currentSortOption == item.sortValue) {
                                LightDark
                                    .copy(alpha = .9f)
                            } else MaterialTheme.colors.onBackground,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )

                        SheetOption(
                            modifier = Modifier
                                .fillMaxWidth(),
                            sortOptionName = when(item.type){
                                Sort.ALPHABETICAL_ASC.type -> LocalStrings.current.alphabeticASC
                                Sort.HIGH_PRICE_FIRST.type -> LocalStrings.current.heightPrice
                                Sort.LOW_PRICE_FIRST.type -> LocalStrings.current.lowPrice
                                Sort.NEW_RELEASE_FIRST.type -> LocalStrings.current.firstRelease
                                Sort.OLD_RELEASE_FIRST.type -> LocalStrings.current.lastRelease
                                else -> LocalStrings.current.alphabeticASC
                            },
                            icon = item.icon,
                            onOptionClicked = {
                                onSortOptionClicked(item.sortValue)
                            },
                            selectedSortColor = selectedColor,
                            contentColor = contentColor
                        )
                    }
                }
                localStrings.languageOption -> {
                    items(Language.values()){item ->
                        val selectedColor by animateColorAsState(
                            targetValue = if (currentLanguageOption == item.languageValue) {
                                MaterialTheme.colors.primary
                                    .copy(alpha = .6f)
                            } else Color.Transparent,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )
                        val contentColor by animateColorAsState(
                            targetValue = if (currentLanguageOption == item.languageValue) {
                                LightDark
                                    .copy(alpha = .9f)
                            } else MaterialTheme.colors.onBackground,
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing
                            )
                        )

                        SheetOptionLanguage(
                            modifier = Modifier
                                .fillMaxWidth(),
                            sortOptionName = if (item.languageName != Language.FOLLOW_SYSTEM.languageName) item.languageName else localStrings.followSystemMode,
                            icon = item.icon,
                            onOptionClicked = {
                                onLanguageOptionClicked(item.languageValue)
                            },
                            selectedSortColor = selectedColor,
                            contentColor = contentColor
                        )
                    }


                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun SettingsEntrySheetPrev() {
    PowerSHTheme {
        SettingEntrySheet(
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.HalfExpanded
            ),
            scope = rememberCoroutineScope(),
            settingsEntryName = Constants.LANGUAGE_OPTIONS,
            currentSortOption = 3,
            currentTheme = 1,
            currentLanguageOption = 2
        )
    }
}