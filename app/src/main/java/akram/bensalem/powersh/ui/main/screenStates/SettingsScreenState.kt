package akram.bensalem.powersh.ui.main.screenStates

sealed class SettingsScreenState {
    data class Settings(
        val themeOption: Int = -1,
        val sortOption: Int = 0
    ): SettingsScreenState()

    companion object {
        val DefaultState = Settings()
    }
}