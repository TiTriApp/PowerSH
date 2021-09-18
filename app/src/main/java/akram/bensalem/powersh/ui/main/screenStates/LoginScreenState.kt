package akram.bensalem.powersh.ui.main.screenStates

import akram.bensalem.powersh.utils.authentification.Authenticate
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue


@Stable
class LoginScreenState {
    var email by mutableStateOf(TextFieldValue(""))
    var password by mutableStateOf(TextFieldValue(""))
    var isNetworkLoading = mutableStateOf(false)
    var networkError by mutableStateOf("")
    var isPasswordForgot = mutableStateOf(false)
    var isLogged = mutableStateOf(false)
}


interface ILoginScreenScope {
    fun signIn()
    fun resetPassword()

    var email: TextFieldValue
    var password: TextFieldValue
    var networkError: String
    var isPasswordForgot: MutableState<Boolean>
    var isLogged: MutableState<Boolean>

}


class LoginScreenScope(
    private val state: LoginScreenState,
    private val authentication: Authenticate,
) : ILoginScreenScope {
    override fun signIn() {
        authentication.signIn(
            state.email.text,
            state.password.text,
            state.isNetworkLoading,
            state.isLogged
        )
    }

    override fun resetPassword() {
        authentication.reinstalisationDeMotDePass(state.email.text)
    }

    override var email: TextFieldValue
        get() = state.email
        set(value) {
            state.email = value
        }
    override var password: TextFieldValue
        get() = state.password
        set(value) {
            state.password = value
        }
    override var networkError: String
        get() = state.networkError
        set(value) {
            state.networkError = value
        }
    override var isPasswordForgot: MutableState<Boolean>
        get() = state.isPasswordForgot
        set(value) {
            state.isPasswordForgot = value
        }


    override var isLogged: MutableState<Boolean>
        get() = state.isLogged
        set(value) {
            state.isLogged = value
        }
}