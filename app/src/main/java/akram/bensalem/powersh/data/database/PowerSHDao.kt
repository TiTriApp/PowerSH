package akram.bensalem.powersh.data.database

import akram.bensalem.powersh.utils.Constants.POWERSH_LIST_TABLE
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PowerSHDao {

    @Query("SELECT * FROM $POWERSH_LIST_TABLE")
    fun getAllShoes(): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query")
    fun searchDatabase(query: String): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query ORDER BY title ASC")
    fun getShoesAlphaAscending(query: String): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query and :index = type ORDER BY title ASC")
    fun getShoesByTabAlphaAscending(query: String, index: Int): Flow<List<PowerSHDatabaseObject>>


    @Query(
        "SELECT * FROM $POWERSH_LIST_TABLE " +
                "WHERE title LIKE :query " +
                "ORDER BY substr (releaseDate, 6, 9) DESC"
    )
    fun getShoesNewestFirst(query: String): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query and :index = type ORDER BY substr (releaseDate, 6, 9) DESC")
    fun getShoesByTabNewestFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>>


    @Query(
        "SELECT * FROM $POWERSH_LIST_TABLE " +
                "WHERE title LIKE :query " +
                "ORDER BY substr (releaseDate, 6, 9) ASC"
    )
    fun getShoesOldestFirst(query: String): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query and :index = type ORDER BY substr (releaseDate, 6, 9) ASC")
    fun getShoesByTabsOldestFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>>


    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query ORDER BY marketPriceStart ASC")
    fun getShoesLowPriceFirst(query: String): Flow<List<PowerSHDatabaseObject>>

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query and :index = type ORDER BY marketPriceStart ASC")
    fun getShoesByTabsLowPriceFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>>


    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query ORDER BY marketPriceStart DESC")
    fun getShoesHighPriceFirst(query: String): Flow<List<PowerSHDatabaseObject>>


    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title LIKE :query and :index = type ORDER BY marketPriceStart DESC")
    fun getShoesByTabsHighPriceFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllShoes(vararg powerSHES: PowerSHDatabaseObject)

    @Query("SELECT * FROM $POWERSH_LIST_TABLE WHERE title = :shoe")
    fun getShoe(shoe: String): Flow<PowerSHDatabaseObject>
}