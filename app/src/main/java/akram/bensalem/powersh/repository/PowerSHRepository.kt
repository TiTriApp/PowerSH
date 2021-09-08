package akram.bensalem.powersh.repository

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import akram.bensalem.powersh.data.api.PowerSHApi
import akram.bensalem.powersh.data.dataTransferObjects.asDatabaseModel
import akram.bensalem.powersh.data.database.PowerSHDao
import akram.bensalem.powersh.data.database.PowerSHDatabaseObject
import akram.bensalem.powersh.data.responses.PowerSHShoesResponse
import akram.bensalem.powersh.utils.Resource
import javax.inject.Inject

@ActivityScoped
class PowerSHRepository @Inject constructor(
    private val powerSHApi: PowerSHApi,
    private val powerSHDao: PowerSHDao
) {

    suspend fun getAllShoesFromNetwork(): Resource<List<PowerSHShoesResponse>> {
        val response = try {
            powerSHApi.getPowerSHSHoes()
        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred: ${e.message}")
        }
        return Resource.Success(data = response)
    }

    fun getAllShoes(): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getAllShoes()
    }

    fun queryShoes(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.searchDatabase("%$query%")
    }

    fun getShoesAlphaAscending(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getShoesAlphaAscending("%$query%")
    }


    fun getShoesNewestFirst(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getShoesNewestFirst("%$query%")
    }

    fun getShoesOldestFirst(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getShoesOldestFirst("%$query%")
    }

    fun getShoesLowPriceFirst(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getShoesLowPriceFirst("%$query%")
    }

    fun getShoesHighPriceFirst(query: String): Flow<List<PowerSHDatabaseObject>> {
        return powerSHDao.getShoesHighPriceFirst("%$query%")
    }

    suspend fun refreshShoesList(powerSHShoesList: List<PowerSHShoesResponse>) {
        withContext(Dispatchers.IO) {
            powerSHDao.insertAllShoes(*powerSHShoesList.asDatabaseModel())
        }
    }

    fun getShoe(shoe: String): Flow<PowerSHDatabaseObject> {
        return powerSHDao.getShoe(shoe = shoe)
    }
}