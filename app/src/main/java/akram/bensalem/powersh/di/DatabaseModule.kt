package akram.bensalem.powersh.di

import akram.bensalem.powersh.data.database.PowerSHDatabase
import akram.bensalem.powersh.utils.Constants.POWERSH_LIST_TABLE
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesShoeDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        PowerSHDatabase::class.java,
        POWERSH_LIST_TABLE
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesShoeDao(database: PowerSHDatabase) = database.powerSHDao
}