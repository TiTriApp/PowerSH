package akram.bensalem.powersh.utils.localization


import akram.bensalem.powersh.data.model.OrderStatus
import akram.bensalem.powersh.ui.theme.Theme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.Sort
import cafe.adriel.lyricist.processor.Strings

@Strings(languageTag = Locales.EN, default = true)
val EnStrings = Strings(
    powerSh = "PowerSH",
    title = "PowerSH",
    madeByAkramBensalem = "Made with ❤ by Akram Bensalem",
    appLogo = "Application Logo",

    akramBensalem = "Akram Bensalem",


    projectMadeBy ="This project is made by:",

    home = "Home",
    cart = "Cart",
    favourite = "Favourites",
    orders = "Orders",
    settings = "Settings",
     aboutUs = "About Us",
     contactUs = "Contact Us",

     signIn = "Sign In",
     creatAccount = "Or create an account",
     logOut  = "Sign Out",

    openMenu = "Open Menu" ,

    // cart screen
    addToCart = "Add To Cart",
    cartIsEmpty = "Cart is Empty",
    totalPrice = "Total Price",
    totalPriceValue = { price ->
        "$price DZD"
    },
    checkout = "Checkout",
    emptyCart = "Empty Cart",


    addToFavourite = "Add To Favourites",
    favouriteListEmpty = "You don't have Favourites yet!",
    totalShoes = "Total of Shoes",
    totalShoesValue = { shoes ->
        when {
            shoes > 1 -> "$shoes item"
            shoes == 1 -> "One item"
            else -> "No Item"
        }
    },
    removeAll = "Remove All",

    yourFullName = "Your Full Name",
    yourEmail ="Your Email",
    yourMessage = "Your Message",
    send = "Send",
    oneFieldOrManyEmpty = "One of filed or many are empty",

    emptyOrderList = "Empty Orders List",
    noOrders = "There are no Orders!",
    totalOrders = "Total of Orders",
    totalOrdersValue = {orders ->
        when {
            orders > 1 -> "$orders Orders"
            orders == 1 -> "One Order"
            else -> "No Order"
        }
    },


    profile = "Profile",
    notification = "Notification",
    inactiveNotification = "Deactivate the Notifications",
    activeNotification = "Activate the Notifications",
    history = "History",
    viewHistory = "View the history of your activity",
    view = "View",
    linkToInvitation = "Link of invitation",
    inviteYourFriend = "Invite your friend",
    invite ="Invite",
    editProfil = "Edite Profile",

    email = "Email Address",
    emailIsNotValid = "This Email is not valid",
    password = "Password",
    forgetPassword = "Forget Password?",
    login = "Login",
    firstName = "First Name",
    tooShort = "Too Short",
    lastName = "Last Name",
    repeatPassword = "Repeat Password",
    repeatPasswordError = "The Repeat password doesn't match your password!",


    passwordTooShort = "Password too short!",
    whiteSpaceAreNorAllowed = "White Space is not allowed!",
    oneDigit = "At least One Digit!",
    oneLowerCase = "At least One Lower Case letter!",
    oneUpperCase = "At least One Upper Case letter!",
    oneLetter = "At least One letter!",
    oneSpecialCharacter = "At least Special Character!",



    dateFormat = "dd/MM/yyyy hh:mm:ss",
    signUp = "Sign Up",

    sentInstructionOfPasswordReset = "We have sent you instructions to reset your password!",
    failedToResetPassword = "Failed to send reset email!",
    authenticationFailed = "Authentication failed.",

    reset = "Reset",
    resetPassword = "Reset Password",
    sortBy = "Sort By",
    close = "Close",
    goBack = "Go Back",
    imageOfPowerSHShoes = "Image of a PowerSH shoes",
    paymentDetail = "Payment Details",
    size = "Size",
    color = "Color",
    quantity = "Quantity",
    add = "Add",
    minus = "Minus",
    detailQuantityValue = {total ->
        "$total DZD"
    },
    reference = "Reference",
    description = "Description",
    typeOfUse = "Type of Use",
    healType = "Heel Type",
    shoeClosure = "Shoe Closure",
    showMoreOrLess = "Show More / Show Less",
    marketValue = "Market Value",
    releaseDate = "Release Date",
    running = "sport (Running)",
    descriptionValue = "Pas de béton ? Pas de problème. Cette chaussure pour homme plaira aux runners qui n'ont pas peur de sortir des sentiers battus. La semelle extérieure en caoutchouc texturée assure une adhérence optimale sur les surfaces glissantes ou accidentées. La semelle intermédiaire allie douceur et réactivité pour t'offrir un confort supérieur.\n" +
            "- tige textile.\n" +
            "- parfaite pour la course à pied.\n" +
            "- matière douce et respirante.\n" +
            "- semelle intermédiaire fuelfoam pour un juste équilibre entre amorti et réactivité.\n" +
            "- semelle extérieure en caoutchouc texturée pour plus d'adhérence et de résistance.",
    scrollUp = "Scroll Up",
    addedToCart = "ADDED TO CART",
    added = "ADDED",
    upload = "UPLOAD",
    uploaded = "UPLOADED",
    selectFavouriteColor = "Select your favourite color",
    selectFavouriteSize = "Select your shoes size",
    id = "ID:",
    date = "Date:",
    total = "Total:",
    status = "Status:",
    statusValue = {status ->
        when(status){
            OrderStatus.PENDING -> "Pending"
            OrderStatus.ACCEPTED -> "Accepted"
            OrderStatus.REJECTED -> "Rejected"
            OrderStatus.DELIVERED -> "Delivered"
        }
    },
    info = "information",
    payment = "Payment:",
    adress = "Address:",
    product = "Products:",
    confirmation = "Confirmation",
    ok = "OK",

    messageConfirmation = "Your message have been sent \nWe will reply soon as possible",
    all = "All",
    men = "Men",
    women = "Women",
    children = "Children",


    confirmYourEmailPlease = "You have to confirm your email, Please!",

    searchByShoesName = "Search by Shoes Name",
    checkYourConnectivity = "Check Your Connectivity",
    problemWithInternetCheckItPlease = "There is a problem with your internet! \n Check it again, please",
    viewCart = "VIEW CART",



    confirmOrderByClicking = "By clicking the button, you confirm your order!",
    confirm = "Confirm",
    cancel = "Cancel",
    versionIs = {version ->
        "Version: $version"
    },
    checkUpdate = "Check Updates",
    yourBilling = "Your Billing",
    to = "To:",
    phoneNumber = "Phone Number:",
    shippingAddress = "Shipping Address:",
    paymentMethod = "Payment Method:",
    ccp = "CCP",
    cashOnDelivery = "Cash On Delivery",
    yourPurchases = "Your Purchases:",

    totalAmount = "Total Amount:",
    wilaya = "Wilaya",
    daira = "Daira",
    commune = "Commune",

    ccpOrBaridiMob = "CCP or BaridiMob",
    payWhenDelivered = "Pay when the products are delivered to you",
    payUsingCCP = "Pay now using your CCP or BaridiMob",

    payViaCCPorBaridiMob = "Pay via CCP or BaridiMob",
    sendTotalAmountPlease = "Please send the total amount that you will pay to this CCP account:",
    ourCCPAccountIs = "Our CCP account: xxxxxxxxxx xx",
    totalAmountValue = {total ->
        "The total amount: $total DZD"
    },
    sendProof = "Then send a proof by uploading an image of the transcription here",
    dearCustomerNotify = "Dear customer, make sure to keep the proof of payment as you will receive a call from our customer service to confirm your order",
    cashDeliveryWillArriveWithin = "In the Cash on delivery payment system, you have to fill the form and we will send you your purchases within 2 days",
    totalAmountYouWillPay = {total ->
        "The total amount you will pay is : ${total} DZD"
    },
    callUs = "CALL US",
    dearCustomerVerifyYourInfo = "Dear customer, make sure to fill your information carefully and you will be receive a call from our customer service to confirm your order",
    back = "Back",
    next = "Next",


    dearCustomerFillTheForm = "Dear customer, make sure to confirm your delivery information by filling this form:",
    phoneNumberIsNotValid = "This Phone number is not valid",
    languageOption = Constants.LANGUAGE_OPTIONS,
    themeOptions = Constants.THEME_OPTIONS,
    lightTheme = Theme.LIGHT_THEME.themeName,
    darkTheme = Theme.DARK_THEME.themeName,
    followSystemMode = "Follow System Settings",
    alphabeticASC = Sort.ALPHABETICAL_ASC.type,
    heightPrice = Sort.HIGH_PRICE_FIRST.type,
    lowPrice = Sort.LOW_PRICE_FIRST.type,
    lastRelease = Sort.NEW_RELEASE_FIRST.type,
    firstRelease = Sort.NEW_RELEASE_FIRST.type
)