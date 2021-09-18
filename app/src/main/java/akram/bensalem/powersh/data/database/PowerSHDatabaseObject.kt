package akram.bensalem.powersh.data.database

import akram.bensalem.powersh.data.types.ShoeType
import akram.bensalem.powersh.utils.Constants.POWERSH_LIST_TABLE
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = POWERSH_LIST_TABLE)
data class PowerSHDatabaseObject(
    @PrimaryKey
    val id: Int,
    val title: String,
    val imageUrl: String,
    val type: ShoeType,
    val releaseDate: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int,
)
