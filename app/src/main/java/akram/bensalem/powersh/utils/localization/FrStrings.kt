package akram.bensalem.powersh.utils.localization

import akram.bensalem.powersh.data.model.OrderStatus
import akram.bensalem.powersh.ui.theme.Theme
import akram.bensalem.powersh.utils.Constants
import akram.bensalem.powersh.utils.Sort

@cafe.adriel.lyricist.processor.Strings(languageTag = Locales.FR)
val FrStrings = Strings(
    powerSh = "PowerSH",
    title = "PowerSH",

    madeByAkramBensalem = "Développée avec ❤ par Akram Bensalem",
    appLogo = "Logo de l'application",

    akramBensalem = "Akram Bensalem",


    projectMadeBy ="Ce projet est réalisé par:",


    home = "Accueil",
    cart = "Panier",
    favourite = "Favorites",
    orders = "Commandes",
    settings = "Paramètres",
    aboutUs = "À propos de nous",
    contactUs = "Contactez-nous",

    signIn = "S'identifier",
    creatAccount = "Ou créez un nouveau compte",
    logOut  = "Se Déconnecter",


    openMenu = "Ouvrir le menu" ,


    // cart screen
    addToCart = "Ajouter au Panier",
    cartIsEmpty = "Le Panier est vide",
    totalPrice = "Prix Total",
    totalPriceValue = { price ->
        "$price DA"
    },
    totalPriceValueString = {price ->
        "$price DA"
    },
    checkout = "Vérifier",
    emptyCart = "Panier vide",




    addToFavourite = "Ajouter aux Favoris",
    favouriteListEmpty = "Vous n'avez pas encore de favoris!",
    totalShoes = "Total des Chaussures",
    totalShoesValue = { shoes ->

        when {
            shoes > 1 -> {
                "$shoes éléments"
            }
            shoes == 1 -> {
                "$shoes élément"
            }
            else -> {
                "aucun élément"
            }
        }

    },
    removeAll = "Enlever tout",

    yourFullName = "Votre Nom Complet",
    yourEmail ="Votre E-mail",
    yourMessage = "Votre Message",
    send = "Envoyer",
    oneFieldOrManyEmpty = "Un des champs ou plusieurs sont vides",

    emptyOrderList = "Liste de commandes vide",
    noOrders = "Il n'y a pas des commandes!",
    totalOrders = "Total des commandes",
    totalOrdersValue = {orders ->
        when {
            orders > 1 -> "$orders Commandes"
            orders == 1 -> "Un seul Commande"
            else -> "Pas de Commande"
        }
    },



    profile = "Profil",
    notification = "Notification",
    inactiveNotification = "Désactiver les notifications",
    activeNotification = "Activer les notifications",
    history = "Historique",
    viewHistory = "Consulter l'historique de votre activité",
    view = "Voir",
    linkToInvitation = "Lien d'invitation",
    inviteYourFriend = "Invitez votre ami",
    invite ="Inviter",
    editProfil = "Modifier le profil",




    email = "Adresse E-mail",
    emailIsNotValid = "Cet E-mail n'est pas valide",
    password = "Mot de Passe",
    forgetPassword = "Mot de Passe oublié?",
    login = "Connexion",
    firstName = "Prénom",
    tooShort = "Trop court",
    lastName = "Nom de Famille",
    repeatPassword = "Répéter le Mot de Passe",
    repeatPasswordError = "Ne correspond pas à votre mot de passe!",


    passwordTooShort = "Mot de passe trop court!",
    whiteSpaceAreNorAllowed = "L'espace blanc n'est pas autorisé!",
    oneDigit = "Au moins un chiffre!",
    oneLowerCase = "Au moins une lettre minuscule!",
    oneUpperCase = "Au moins une lettre majuscule!",
    oneLetter = "Au moins une lettre!",
    oneSpecialCharacter = "Au moins un caractère spécial!",



    dateFormat = "dd/MM/yyyy hh:mm:ss",
    signUp = "S'inscrire",

    sentInstructionOfPasswordReset = "Nous vous avons envoyé des instructions pour réinitialiser votre mot de passe!",
    failedToResetPassword = "Échec de l'envoi de l'e-mail de réinitialisation!",
    authenticationFailed = "Authentification échouée.",








    reset = "Réinitialiser",
    resetPassword = "Réinitialiser le mot de passe",
    sortBy = "Trier Par",
    close = "Fermer",
    goBack = "Retourner",
    imageOfPowerSHShoes = "Image d'une chaussure PowerSH",
    paymentDetail = "Détails de paiement",
    size = "Taille",
    color = "Couleur",
    quantity = "Quantité",
    add = "Ajouter",
    minus = "Moins",
    detailQuantityValue = {total ->
        "$total DA"
    },
    reference = "Référence",
    description = "La description",
    typeOfUse = "Type d'Utilisation",
    healType = "Type de Talon",
    shoeClosure = "Fermeture",
    showMoreOrLess = "Afficher plus / Afficher moins",
    marketValue = "Prix",
    releaseDate = "Date de sortie",
    running = "Sport (Course à pied)",
    descriptionValue = "Pas de béton ? Pas de problème. Cette chaussure pour homme plaira aux runners qui n'ont pas peur de sortir des sentiers battus. La semelle extérieure en caoutchouc texturée assure une adhérence optimale sur les surfaces glissantes ou accidentées. La semelle intermédiaire allie douceur et réactivité pour t'offrir un confort supérieur.\n" +
            "- tige textile.\n" +
            "- parfaite pour la course à pied.\n" +
            "- matière douce et respirante.\n" +
            "- semelle intermédiaire fuelfoam pour un juste équilibre entre amorti et réactivité.\n" +
            "- semelle extérieure en caoutchouc texturée pour plus d'adhérence et de résistance.",
    scrollUp = "faire remonter",
    addedToCart = "AJOUTÉ AU PANIER",
    added = "AJOUTÉE",
    upload = "TÉLÉCHARGER",
    uploaded = "TÉLÉCHARGÉ",
    selectFavouriteColor = "Sélectionnez votre couleur préférée",
    selectFavouriteSize = "Sélectionnez votre pointure de chaussures",
    id = "ID:",
    date = "Date:",
    total = "Total:",
    status = "Statut:",
    statusValue = {status ->
        when(status){
            OrderStatus.PENDING -> "En attente"
            OrderStatus.ACCEPTED -> "Accepté"
            OrderStatus.REJECTED -> "Rejetée"
            OrderStatus.DELIVERED -> "Livré"
        }
    },
    info = "informations",
    payment = "Paiement:",
    adress = "Adresse:",
    product = "Les produits:",
    confirmation = "Confirmation",
    ok = "D'accord",

    messageConfirmation = "Votre message a été envoyé\nNous vous répondrons dans les plus brefs délais",
    all = "Tout",
    men = "Hommes",
    women = "Femmes",
    children = "Enfants",


    confirmYourEmailPlease = "Vous devez confirmer votre e-mail, s'il vous plaît!",

    searchByShoesName = "Recherche par nom de chaussures",
    checkYourConnectivity = "Vérifiez votre connectivité",
    problemWithInternetCheckItPlease = "Il y a un problème avec votre internet !\nVérifiez à nouveau, s'il vous plaît",
    viewCart = "VOIR LE PANIER",





    confirmOrderByClicking = "En cliquant sur le bouton, vous confirmez votre commande!",
    confirm = "Confirmer",
    cancel = "Annuler",
    versionIs = {version ->
        "Version: $version"
    },
    checkUpdate = "Mise à jour",
    yourBilling = "Votre facturation",
    to = "À:",
    phoneNumber = "Numéro de téléphone:",
    shippingAddress = "Adresse de livraison:",
    paymentMethod = "Mode de paiement:",
    ccp = "CCP",
    cashOnDelivery = "Paiement à la livraison",
    yourPurchases = "Vos achats:",

    totalAmount = "Montant total:",
    wilaya = "Wilaya",
    daira = "Daira",
    commune = "Commune",

    ccpOrBaridiMob = "CCP ou BaridiMob",
    payWhenDelivered = "Payez lorsque les produits vous sont livrés",
    payUsingCCP = "Payez maintenant avec votre CCP ou BaridiMob",

    payViaCCPorBaridiMob = "Payer via CCP ou BaridiMob",
    sendTotalAmountPlease = "Veuillez envoyer le montant total que vous paierez sur ce compte CCP:",
    ourCCPAccountIs = "Notre compte CCP : xxxxxxxxxx xx",
    totalAmountValue = {total ->
        "Le montant total: $total DA"
    },
    sendProof = "Envoyez ensuite une épreuve en téléchargeant une image de la transcription ici",
    dearCustomerNotify = "Cher client, assurez-vous de conserver la preuve de paiement car vous recevrez un appel de notre service client pour confirmer votre commande",
    cashDeliveryWillArriveWithin = "Dans le système de paiement contre remboursement, vous devez remplir le formulaire et nous vous enverrons vos achats sous 2 jours",
    totalAmountYouWillPay = {total ->
        "Le montant total que vous paierez est: ${total} DA"
    },
    callUs = "APPELEZ-NOUS",
    dearCustomerVerifyYourInfo = "Cher client, assurez-vous de bien remplir vos informations et vous recevrez un appel de notre service client pour confirmer votre commande",
    back = "Précédent",
    next = "Suivant",






    dearCustomerFillTheForm = "Cher client, assurez-vous de confirmer vos informations de livraison en remplissant ce formulaire :",
    phoneNumberIsNotValid = "Ce numéro de téléphone n'est pas valide",
    languageOption = "Option de Langue",
    themeOptions = "Options du Thème",
    sortOptions = "Option de tri par défaut" ,
    lightTheme = "Thème de la lumière",
    darkTheme = "Thème sombre",
    followSystemMode = "Suivre les paramètres système",
    alphabeticASC = "Ordre Alphabétique",
    heightPrice = "Prix le plus élevé d'abord",
    lowPrice = "Prix le plus bas d'abord",
    lastRelease = "Version la plus ancienne en premier",
    firstRelease = "Dernière version d'abord",

    fullAddress ="Adresse Complète",

            contactFailed = "Échec de l'envoi du message!",
    open = "Ouvrir",

    billingHaveBeenPrinted = "La facture a été imprimée!",
    at = {time ->
        "À $time"}


    )