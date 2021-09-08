package akram.bensalem.powersh.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import akram.bensalem.powersh.utils.Constants.POWERSH_LIST_TABLE



@Entity(tableName = POWERSH_LIST_TABLE)
data class PowerSHDatabaseObject(
    @PrimaryKey
    val id:Int,
    val model: String,
    val imageUrl: String,
    val releaseDate: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int,
)
