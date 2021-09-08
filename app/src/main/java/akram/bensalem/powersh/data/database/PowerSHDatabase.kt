package akram.bensalem.powersh.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PowerSHDatabaseObject::class],
    version = 2
)
abstract class PowerSHDatabase: RoomDatabase() {
    abstract val powerSHDao: PowerSHDao
}