package akram.bensalem.powersh.utils.localization

import akram.bensalem.powersh.data.model.OrderStatus
import com.akram.bensalem.powersh.ui.R.string

data class Strings(
    val powerSh: String,
    val title: String,
    val madeByAkramBensalem : String,
    val appLogo : String,

    val akramBensalem : String,


    val projectMadeBy : String,



    val home : String,
    val cart : String,
    val favourite : String,
    val orders : String,
    val settings : String,
    val aboutUs : String,
    val contactUs : String,

    val signIn : String,
    val creatAccount : String,
    val logOut  : String,

    val openMenu  : String,


    val addToCart  : String,
    val cartIsEmpty : String,
    val totalPrice: String,
    val totalPriceValue: (totalPrice: Int) -> String,
    val checkout : String,
    val emptyCart : String,


    val addToFavourite: String,
    val favouriteListEmpty : String,
    val totalShoes : String,
    val totalShoesValue: (totalShoes: Int) -> String,
    val removeAll : String,


    val yourFullName : String,
    val yourEmail : String,
    val yourMessage : String,

    val send : String,

    val oneFieldOrManyEmpty : String,


    val emptyOrderList : String,
    val noOrders : String,
    val totalOrders :String,
    val totalOrdersValue: (totalOrders: Int) -> String,


    val profile : String,
    val notification : String,
    val inactiveNotification : String,
    val activeNotification : String,
    val history : String,
    val viewHistory : String,
    val view : String,
    val linkToInvitation : String,
    val inviteYourFriend : String,
    val invite : String,
    val editProfil : String,



    val email : String,
    val emailIsNotValid : String,
    val password : String,
    val forgetPassword : String,
    val login : String,

    val firstName : String,
    val tooShort : String,
    val lastName : String,
    val repeatPassword :String,
    val repeatPasswordError :String,

    val passwordTooShort : String,
    val whiteSpaceAreNorAllowed :String,
    val oneDigit : String,
    val oneLowerCase: String,
    val oneUpperCase : String,
    val oneLetter : String,
    val oneSpecialCharacter :String,


    val dateFormat : String,
    val signUp: String,


    val sentInstructionOfPasswordReset: String,
    val failedToResetPassword : String,
    val authenticationFailed : String,


    val reset : String,
    val resetPassword : String,

    val sortBy : String,
    val close : String,
    val goBack : String,
    val imageOfPowerSHShoes : String,
    val paymentDetail : String,
    val size : String,
    val color : String,
    val quantity : String,
    val add: String,
    val minus : String,
    val detailQuantityValue: (total: Int) -> String,
    val reference : String,
    val description : String,
    val typeOfUse : String,
    val healType : String,
    val shoeClosure : String,
    val showMoreOrLess : String,
    val marketValue : String,
    val releaseDate : String,
    val running : String,
    val descriptionValue : String,


    val scrollUp : String,
    val addedToCart : String,
    val added : String,
    val upload : String,
    val uploaded :String,

    val selectFavouriteColor: String,
    val selectFavouriteSize : String,

    val id : String,
    val date : String,
    val total : String,
    val status : String,
    val statusValue: (total: OrderStatus) -> String,
    val info : String,
    val payment : String,
    val adress : String,
    val product : String,

    val confirmation : String,
    val ok : String,

    val messageConfirmation : String,


    val all : String,
    val men :String,
    val women :String,
    val children : String,

    val confirmYourEmailPlease : String,


    val searchByShoesName : String,

    val checkYourConnectivity : String,

    val problemWithInternetCheckItPlease: String,
    val viewCart : String,

    val confirmOrderByClicking :String,
    val confirm : String,
    val cancel : String,

    val versionIs: (version: String) -> String,
    val checkUpdate : String,
    val yourBilling :String,
    val to:String,
    val phoneNumber : String,
    val shippingAddress: String,
    val paymentMethod: String,
    val ccp : String,
    val cashOnDelivery : String,
    val yourPurchases: String,

    val totalAmount: String,
    val wilaya:String,
    val daira : String,
    val commune :String,

    val ccpOrBaridiMob : String,
    val payWhenDelivered : String,
    val payUsingCCP :String,

    val payViaCCPorBaridiMob:String,
    val sendTotalAmountPlease:String,
    val ourCCPAccountIs:String,

    val totalAmountValue: (total : Int) -> String ,
    val sendProof :String,
    val dearCustomerNotify : String,
    val cashDeliveryWillArriveWithin : String,

    val totalAmountYouWillPay : (Int) -> String,
    val callUs :String,

    val dearCustomerVerifyYourInfo :String,

    val back : String,
    val next :String,

    )
