package akram.bensalem.powersh.utils.localization
import akram.bensalem.powersh.data.model.OrderStatus
import cafe.adriel.lyricist.processor.Strings


@Strings(languageTag = Locales.AR)
val ArStrings = Strings(
    powerSh = "PowerSH",
    title = "الصفحة الرئيسية",


    madeByAkramBensalem = "طور من طرف أكرم بن سالم ❤",
    appLogo = "شعار التطبيق",

    akramBensalem = "أكرم بن سالم",


    projectMadeBy ="هذا المشروع من برمجة وتطوير:",


    home = "الصفحة الرئيسية",
    cart = "سلة المشتريات",
    favourite = "قائمة المفضلة",
    orders = "قائمة الطلبات",
    settings = "الإعدادات",
    aboutUs = "حولنا",
    contactUs = "تواصل معنا",

    signIn = "تسجيل الدخول",
    creatAccount = "أو قم بإنشاء حساب جديد",
    logOut  = "تسجيل الخروج",

    openMenu = "افتح القائمة" ,


    // cart screen
    addToCart = "أضف إلى السلة",
    cartIsEmpty = "سلةالمشتريات فارغة",
    totalPrice = "السعر الكلي",
    totalPriceValue = { price ->
        "$price دج "
    },
    totalPriceValueString ={ price ->
        "$price دج "
    },
    checkout = "ادفع الآن",
    emptyCart = "سلة فارغة",





    addToFavourite = "أضف إلى المفضلة",
    favouriteListEmpty = "ليس لديك المفضلة بعد!",
    totalShoes = "مجموع الأحذية",
    totalShoesValue = { shoes ->
        "$shoes عنصر "


        when {
            shoes > 1 -> {
                "$shoes عناصر "
            }
            shoes == 1 -> {
                "عنصر واحد"
            }
            else -> {
                "لا يوجد أي عنصر"
            }
        }

    },
    removeAll = "حذف الكل",

    yourFullName = "اسمك الكامل",
    yourEmail ="بريدك الالكتروني",
    yourMessage = "رسالتك",
    send = "أرسل",
    oneFieldOrManyEmpty = "واحد من الحقول أو العديد منها فارغة",

    emptyOrderList = "قائمة الطلبات الفارغة",
    noOrders = "لا توجد طلبات!",
    totalOrders = "مجموع الطلبات",
    totalOrdersValue = {orders ->
        when {
            orders > 1 -> "$orders طلبات "
            orders == 1 -> "طلب واحد"
            else -> "لا يوجد طلبات"
        }
    },


    profile = "الملف الشخصي",
    notification = "إشعارات",
    inactiveNotification = "قم بإلغاء تنشيط الإشعارات",
    activeNotification = "تفعيل الإشعارات",
    history = "تاريخ نشاطك",
    viewHistory = "عرض تاريخ نشاطك",
    view = "شاهد",
    linkToInvitation = "رابط الدعوة",
    inviteYourFriend = "ادعو صديقك",
    invite ="ادعوا",
    editProfil = "عدل الملف الشخصي",

    email = "البريد الإلكتروني",
    emailIsNotValid = "هذا البريد الإلكتروني غير صالح",
    password = "كلمة السر",
    forgetPassword = "نسيت كلمة المرور؟",
    login = "تسجيل الدخول",
    firstName = "الاسم الأول",
    tooShort = "قصيرة جدا",
    lastName = "اسم العائلة",
    repeatPassword = "اعد كلمة السر",
    repeatPasswordError = "تكرار كلمة المرور لا يتطابق مع كلمة المرور الخاصة بك!",


    passwordTooShort = "كلمة المرور قصيرة جدًا!",
    whiteSpaceAreNorAllowed = "الفراغ غير مسموح به!",
    oneDigit = "رقم واحد على الأقل!",
    oneLowerCase = "حرف لاتيني صغير واحد على الأقل!",
    oneUpperCase = "حرف لاتيني كبير واحد على الأقل!",
    oneLetter = "حرف لاتيني واحد على الأقل!",
    oneSpecialCharacter = "رمز خاص واحد على الأقل!",



    dateFormat = "hh:mm:ss yyyy/MM/dd",
    signUp = "اشتراك",

    sentInstructionOfPasswordReset = "لقد أرسلنا لك تعليمات لإعادة تعيين كلمة المرور الخاصة بك!",
    failedToResetPassword = "فشل في إرسال إستعادة كلمة المرور!",
    authenticationFailed = "المصادقة فشلت.",




    reset = "إعادة ضبط",
    resetPassword = "إعادة تعيين كلمة المرور",
    sortBy = "صنف حسب",
    close = "أغلق",
    goBack = "عد",
    imageOfPowerSHShoes = "صورة حذاء PowerSH",
    paymentDetail = "بيانات الدفع",
    size = "المقاس",
    color = "اللون",
    quantity = "الكمية",
    add = "أضف",
    minus = "أنقص",
    detailQuantityValue = {total ->
        "$total دج "
    },
    reference = "المرجع",
    description = "الوصف",
    typeOfUse = "نوع الاستخدام",
    healType = "نوع الكعب",
    shoeClosure = "إغلاق الحذاء",
    showMoreOrLess = "إظهار المزيد / إظهار أقل",
    marketValue = "القيمة السوقية",
    releaseDate = "تاريخ الصتع",
    running = "رياضة (جري)",
    descriptionValue = "سوف يروق هذا الحذاء الرجالي للعدائين الذين لا يخشون التفكير خارج الصندوق. يوفر النعل الخارجي المطاطي المحكم قبضة مثالية على الأسطح الزلقة أو الخشنة. يجمع النعل الأوسط بين النعومة والاستجابة ليمنحك راحة فائقة.",
    scrollUp = "انتقل إلى أعلى",
    addedToCart = "أضف إلى سلة المشتريات",
    added = "مضاف",
    upload = "تحميل",
    uploaded = "تم التحميل",
    selectFavouriteColor = "اختر لونك المفضل",
    selectFavouriteSize = "حدد مقاس حذائك",
    id = "الرقم التعريفي:",
    date = "التاريخ:",
    total = "المجموع:",
    status = "الحالة:",
    statusValue = {status ->
        when(status){
            OrderStatus.PENDING -> "قيد الانتظار"
            OrderStatus.ACCEPTED -> "وافقت"
            OrderStatus.REJECTED -> "مرفوض"
            OrderStatus.DELIVERED -> "تم التوصيل"
        }
    },
    info = "معلومات",
    payment = "الدفع:",
    adress = "العنوان:",
    product = "المنتجات:",
    confirmation = "تأكيد",
    ok = "حسنا",

    messageConfirmation = "تم إرسال رسالتك\n" +
            "سوف نقوم بالرد في أقرب وقت ممكن",
    all = "الكل",
    men = "رجال",
    women = "نساء",
    children = "أطفال",


    confirmYourEmailPlease = "يجب عليك تأكيد بريدك الإلكتروني ، من فضلك!",

    searchByShoesName = "البحث من خلال اسم الحذاء",
    checkYourConnectivity = "تحقق من الاتصال الخاص بك",
    problemWithInternetCheckItPlease = "هناك مشكلة في الإنترنت الخاص بك!\n" +
            " تحقق من ذلك مرة أخرى ، من فضلك",
    viewCart = "عرض سلة المشتريات",






    confirmOrderByClicking = "بالنقر فوق الزر ، تؤكد طلبك!",
    confirm = "تأكيد",
    cancel = "إلغاء",
    versionIs = {version ->
          "إصدار: $version"
    },
    checkUpdate = "تفقد آخر تحديث",
    yourBilling = "الفاتورة الخاصة بك",
    to = "إلى:",
    phoneNumber = "رقم الهاتف:",
    shippingAddress = "عنوان الشحن:",
    paymentMethod = "طريقة الدفع او السداد:",
    ccp = "حساب البريد CCP",
    cashOnDelivery = "الدفع عند الاستلام",
    yourPurchases = "مشترياتك:",

    totalAmount = "المبلغ الإجمالي:",
    wilaya = "الولاية",
    daira = "الدائرة",
    commune = "البلدية",

    ccpOrBaridiMob = "حساب البريد الجاري أو بريدي موب",
    payWhenDelivered = "ادفع عند استلام المنتجات الخاصة بك",
    payUsingCCP = "ادفع الآن باستخدام حساب البريد الجاري أو بريدي موب",

    payViaCCPorBaridiMob = "ادفع الآن باستخدام حساب البريد الجاري أو بريدي موب",
    sendTotalAmountPlease = "الرجاء إرسال المبلغ الإجمالي الذي ستدفعه لحساب CCP هذا:",
    ourCCPAccountIs = "حساب CCP الخاص بنا: xxxxxxxxxx xx",
    totalAmountValue = {total ->
        "المبلغ الكلي: $total دج"
    },
    sendProof = "ثم أرسل إثباتًا عن طريق تحميل صورة للنسخ هنا",
    dearCustomerNotify = "عزيزي العميل ، تأكد من الاحتفاظ بإثبات الدفع حيث ستتلقى مكالمة من خدمة العملاء لتأكيد طلبك",
    cashDeliveryWillArriveWithin = "في نظام الدفع نقدًا عند الاستلام ، يجب عليك ملء النموذج وسنرسل إليك مشترياتك في غضون يومين",
    totalAmountYouWillPay = {total ->
        "المبلغ الإجمالي الذي ستدفعه هو: $total دج"
    },
    callUs = "اتصل بنا",
    dearCustomerVerifyYourInfo = "عزيزي العميل تأكد من تعبئة معلوماتك بعناية وستتلقى مكالمة من خدمة العملاء لتأكيد طلبك",
    back = "السابق",
    next = "التالي",



    dearCustomerFillTheForm = "عزيزي العميل ، تأكد من تأكيد معلومات التسليم الخاصة بك عن طريق ملء هذا النموذج:",
    phoneNumberIsNotValid = "رقم الهاتف هذا غير صالح",
    languageOption = "خيار اللغة",
    themeOptions = "حالة الإضاءة",
    sortOptions = "خيار خاصية الفرز الافتراضي" ,

    lightTheme = "وضع النهار",
    darkTheme = "وضع الليلي",
    followSystemMode = "تتبع إعدادات نظام",
    alphabeticASC = "ترتيب أبجدي",
    heightPrice = "اعلى سعر اولا",
    lowPrice = "أقل سعر أولا",
    lastRelease = "الإصدار الأقدم أولاً",
    firstRelease = "أحدث إصدار أولا",
    fullAddress = "العنوان الكامل",
    contactFailed = "فشل في إرسال الرسالة!",

    open = "إفتح",

    billingHaveBeenPrinted = "تمت طباعة الفاتورة!",
    at = {time ->
        "على $time"},

    entrerEnContact = "تواصل معنا",

    black = "أسود",
    red = "أحمر",
    white = "أبيض",
    blue = "أزرق",
    brown = "بني"



)