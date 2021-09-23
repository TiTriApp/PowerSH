package akram.bensalem.powersh.data.model

import androidx.compose.runtime.Immutable


@Immutable
data class Review(
    val id: Long = 1,
    val userName: String = "Akram Bensalem",
    val userAvatarUrl: String = "",
    val reviewDesc: String = "Amazing Shoes!",
    val ratings: Double = 4.5,
    val date: String = "9/25/20",
    val appId: Long = 1
)



val reviews = listOf(
    Review(
        id = 1L,
        userName = "Alicia Mayer",
        userAvatarUrl = "https://i.pinimg.com/564x/33/a2/d4/33a2d4e2aef856528a8696e83651e5a9.jpg",
        reviewDesc = "A true delight. Never stop development, its wonderful. Amazing app! Just love it!",
        ratings = 4.5,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "Edward Frost",
        userAvatarUrl = "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=900&q=60",
        reviewDesc = "Amazing experience. The animations are really smooth. A true joy ride! Would suggest to have more features in the future.",
        ratings = 4.0,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "Vicky Sharma",
        userAvatarUrl = "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1600&q=80",
        reviewDesc = "Bag experience. App keeps on crashing whenever I go to Apps section. I don't know what happened to this app . It was one of the best apps but now it's the worst. Sometimes not able to navigate to Movies section from Apps, also search option doesn't work everytime.",
        ratings = 2.5,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "Christopher Julie",
        userAvatarUrl = "https://i.pinimg.com/236x/e7/1f/fc/e71ffc23cf4c38cf1eea484c344d2e22.jpg",
        reviewDesc = "Nice app, needs lots of improvement though. Would love to use it for the rest of my life. ",
        ratings = 3.0,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "Mohit Singh",
        userAvatarUrl = "https://images.unsplash.com/photo-1568602471122-7832951cc4c5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=900&q=60",
        reviewDesc = "Worst app! Uninstalled it. Thank you.",
        ratings = 2.0,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "Tom Hanks",
        userAvatarUrl = "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2550&q=80",
        reviewDesc = "App is good but I'm not feeling comfortable with so many permissions, especially fine location and camera, I read the explanation for the necessity but I'm not buying it, camera & location permissions could have been asked when and if I wanted to share something to friends.",
        ratings = 4.0,
        date = "9/25/20",
        appId = 1L
    ),
    Review(
        id = 1L,
        userName = "R Chandler",
        userAvatarUrl = "https://images.unsplash.com/photo-1601568544619-b03062ca528a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2500&q=80",
        reviewDesc = "Nice app. Like using it. Feels like I'm using the playstore app. But since I've updated the app to the latest version, it has become laggy and gets crashed frequently.",
        ratings = 3.0,
        date = "9/25/20",
        appId = 1L
    )
)