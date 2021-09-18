package akram.bensalem.powersh.data.database

import akram.bensalem.powersh.data.types.Converter
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PowerSHDatabaseObject::class],
    version = 2
)

@TypeConverters(Converter::class)
abstract class PowerSHDatabase : RoomDatabase() {
    abstract val powerSHDao: PowerSHDao
}