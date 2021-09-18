package akram.bensalem.powersh.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FireBaseUser(
    val providerId: String,
    val uid: String,
    val name: String,
    val email: String,
    val photoUrl: Uri,
) : Parcelable