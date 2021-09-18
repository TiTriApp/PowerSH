package akram.bensalem.powersh.ui.main.screens.login

import junit.framework.TestCase
import org.junit.Test

class LoginScreenKtTest : TestCase(){

    @Test
 fun TestPaasword(){

     var error = ErrorMessageOfPassword(password ="12345678")

     assertEquals("At least One Digit!", error)

 }

}