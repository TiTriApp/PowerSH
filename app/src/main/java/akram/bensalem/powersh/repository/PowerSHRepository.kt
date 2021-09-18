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
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

@ActivityScoped
class PowerSHRepository @Inject constructor(
    private val powerSHApi: PowerSHApi,
    private val powerSHDao: PowerSHDao
) {


    private val firestore = FirebaseFirestore.getInstance()



 /*   suspend fun getAllShoesFromNetwork2() : Resource<List<PowerSHShoesResponse>> {

        val collection = firestore.collection("shoes")
        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null) {
                OnSuccess(value)
            } else {
                OnError(error)
            }

            offer(response)
        }

        awaitClose {
            snapshotListener.remove()
        }
    }*/



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

    fun getShoesAlphaAscending(query: String, index:Int): Flow<List<PowerSHDatabaseObject>> {
        return if (index != 0){
            powerSHDao.getShoesByTabAlphaAscending(query = "%$query%", index = index)

        }else {
            powerSHDao.getShoesAlphaAscending(query = "%$query%")

        }

    }


    fun getShoesNewestFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>> {
        return if (index != 0){
            powerSHDao.getShoesByTabNewestFirst(query = "%$query%", index = index)

        }else {
            powerSHDao.getShoesNewestFirst("%$query%")

        }
    }

    fun getShoesOldestFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>> {

        return if (index != 0){
            powerSHDao.getShoesByTabsOldestFirst(query = "%$query%", index = index)

        }else {
            powerSHDao.getShoesOldestFirst("%$query%")

        }

    }

    fun getShoesLowPriceFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>> {


        return if (index != 0){
            powerSHDao.getShoesByTabsLowPriceFirst(query = "%$query%", index = index)

        }else {
            powerSHDao.getShoesLowPriceFirst("%$query%")

        }


    }

    fun getShoesHighPriceFirst(query: String, index: Int): Flow<List<PowerSHDatabaseObject>> {

        return if (index != 0){
            powerSHDao.getShoesByTabsHighPriceFirst(query = "%$query%", index = index)

        }else {
            powerSHDao.getShoesHighPriceFirst("%$query%")

        }


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