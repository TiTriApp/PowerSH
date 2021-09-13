package akram.bensalem.powersh.utils

import akram.bensalem.powersh.R
import akram.bensalem.powersh.data.model.CardItem
import akram.bensalem.powersh.data.model.ShoeProduct
import akram.bensalem.powersh.data.model.Step
import akram.bensalem.powersh.data.types.ShoeType
import androidx.compose.runtime.mutableStateListOf

object Constants {
 //  const val BASE_URL = "https://docs.google.com/spreadsheets/d/1cFrYzzAP7i3bzSLKuBMykz3ZNUbf-YPTqRSEAwINy_E/"

   const val BASE_URL = "https://docs.google.com/spreadsheets/d/1ryFLEuVNeEnzczIWqeJ9pLP3doZA7Vr1g5qdTdThj_o/"
    const val POWERSH_LIST_TABLE = "thinkpad_list"
    const val PREFERENCE_NAME = "settings_preference"
    const val THEME_OPTIONS = "Theme Options"
    const val SORT_OPTIONS = "Default Sort Option"

    val ShoesListPreview = listOf(
        ShoeProduct(
            id = 0,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.MEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 1,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.MEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 2,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.WOMEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 3,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.WOMEN,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        ),
        ShoeProduct(
            id = 4,
            title = "Thinkpad T450",
            imageUrl = "https://raw.githubusercontent.com/racka98/ThinkRchive/main/thinkpad_images/thinkpadt450.png",
            type= ShoeType.BABY,
            releaseDate = "Jan, 2015",
            marketPriceStart = 250,
            marketPriceEnd = 500,
        )
    )


    val stepList = mutableStateListOf<Step>(

        Step(
            title = "Payment",
        ),
        Step(
            title = "Address",
        ),
        Step(
            title = "Confirmation",
        ),
    )




    val wilayaList = mutableStateListOf<String>(
        "Wilaya",
        "Adrar",
        "Chlef",
        "Laghouat",
        "OumElBowaghi",
        "Batna",
        "Bejaia",
        "Biskra",
        "Bechar",
        "Blida",
        "Bouira",
        "Tamanrasset",
        "Tebessa",
        "Tlemcen",
        "Tiaret",
        "Tiziouzou",
        "Alger",
        "Djelfa",
        "Jijel",
        "Setif",
        "Saida",
        "Skikda",
        "Sidibelabbes",
        "Annaba",
        "Guelma",
        "Constantine",
        "Medea",
        "Mostaghanem",
        "Msila",
        "Mascara",
        "Owargla",
        "Oran",
        "ElBayadh",
        "Illizi",
        "BorjbouArrieridj",
        "Boumerdes",
        "ElTarif",
        "Tindouf",
        "Tissemsilt",
        "Eloued",
        "Khanchela",
        "SoukAhras",
        "Tipaza",
        "Mila",
        "AinDefla",
        "Naama",
        "Ain Temouchent",
        "Ghadaia",
        "Relizane",
        "ElMghair",
        "ElMeniaa",
        "Ouled Djelal",
        "BorjBajiMokhtar",
        "Beni Abbes",
        "Timimoun",
        "Touggourt",
        "Djanet",
        "In Salah",
        "In Guezzan",
    )






    val cartList2 = mutableStateListOf<CardItem>(

        CardItem(
            quantity = 1,
            color = "White",
            size = 38,
            id = 1,
            title = "Basket",
            description = "Les chaussures de basket-ball sont les chaussures utilisées par les pratiquants du basket-ball. Bien qu'apparentées aux baskets classiques, elles ont une forme spécifique : elles sont montantes, afin de cacher la malléole médiale et de minimiser les risques de torsion de la cheville.",
            price = 7000,
            ImageId = "R.drawable.baby"
        ),
        CardItem(
            quantity = 2,
            color = "Blue",
            size = 37,
            id = 2,
            title = "Sneaker",
            price = 6000,
            ImageId = "R.drawable.men",
            description = "Les sneakers désignent une paire de chaussures de sport détournée à un usage citadin et quotidien. Dérivées des modèles conçus pour le sport, elles sont appréciées pour leur confort et leur style.",
        ),
    )







    val cartList = mutableStateListOf<CardItem>(

        CardItem(
            quantity = 1,
            description = "Les chaussures de basket-ball sont les chaussures utilisées par les pratiquants du basket-ball. Bien qu'apparentées aux baskets classiques, elles ont une forme spécifique : elles sont montantes, afin de cacher la malléole médiale et de minimiser les risques de torsion de la cheville.",
            color = "White",
            size = 38,
            id = 1,
            title = "Basket",
            price = 7000,
            ImageId = "R.drawable.baby"
        ),
        CardItem(
            quantity = 2,
            description = "Les sneakers désignent une paire de chaussures de sport détournée à un usage citadin et quotidien. Dérivées des modèles conçus pour le sport, elles sont appréciées pour leur confort et leur style.",
            color = "Blue",
            size = 37,
            id = 2,
            title = "Sneaker",
            price = 6000,
            ImageId = "R.drawable.men"
        ),
        CardItem(
            quantity = 2,
            color = "Black",
            size = 41,
            id = 3,
            title = "Sebago Classic Dan Waxy, Mocassins (Loafer)",
            description = "Dessus: Autres Cuir\n" +
                    "Doublure: Cuir\n" +
                    "Semelle intérieure: Cuir\n" +
                    "Matériau de semelle: Cuir\n" +
                    "Largeur de la chaussure: Moyen\n" +
                    "Fermeture: Enfiler",
            price = 8000,
            ImageId = "R.drawable.baby,"
        ),
        CardItem(
            quantity = 1,
            color = "Blue",
            description ="Basket Versace\n" +
                    "Version basse\n" +
                    "Bout de la chaussure : Rond\n" +
                    "Fermeture : À lacets\n" +
                    "Dessus/Tige : Cuir / Synthétique\n" +
                    "Doublure : Textile\n" +
                    "Semelle intérieure : Textile\n" +
                    "Semelle extérieure : Caoutchouc\n" +
                    "Versace imprimé sur la languette\n" +
                    "Confort exceptionnel\n" +
                    "Composition : Cuir et Synthétique",
            size = 39,
            id = 4,
            title = "Basket Versace=",
            price = 4000,
            ImageId = "R.drawable.men"
        ),
        CardItem(
            quantity = 1,
            color = "Brown",
            size = 42,
            id = 5,
            title = "Timberland Classic Unlined",
            description ="Dessus: Cuir\n" +
                    "Doublure: Textile\n" +
                    "Semelle intérieure: Textile\n" +
                    "Matériau de semelle: Caoutchouc\n" +
                    "Type de talons: Plat\n" +
                    "Largeur de la chaussure: Medium\n" +
                    "Fermeture: Lacet",
            price = 3000,
            ImageId = "R.drawable.woman2"
        ),
    )




}